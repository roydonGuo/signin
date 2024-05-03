package com.roydon.business.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roydon.business.app.domain.dto.SigninTaskCrateDTO;
import com.roydon.business.app.domain.dto.SigninTaskPageDTO;
import com.roydon.business.app.domain.entity.Lesson;
import com.roydon.business.app.domain.entity.LessonClass;
import com.roydon.business.app.domain.entity.SigninRecord;
import com.roydon.business.app.domain.entity.SigninTask;
import com.roydon.business.app.domain.enums.DelFlagEnum;
import com.roydon.business.app.domain.vo.PageDataInfo;
import com.roydon.business.app.domain.vo.SigninRecordVO;
import com.roydon.business.app.domain.vo.SigninTaskVO;
import com.roydon.business.app.mapper.SigninTaskMapper;
import com.roydon.business.app.service.*;
import com.roydon.common.core.domain.entity.SysUser;
import com.roydon.common.utils.SecurityUtils;
import com.roydon.common.utils.bean.BeanCopyUtils;
import com.roydon.system.service.ISysUserService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 签到任务表(SigninTask)表服务实现类
 *
 * @author roydon
 * @since 2024-02-16 16:43:48
 */
@Service("signinTaskService")
public class SigninTaskServiceImpl extends ServiceImpl<SigninTaskMapper, SigninTask> implements ISigninTaskService {
    @Resource
    private SigninTaskMapper signinTaskMapper;

    @Resource
    private ILessonClassService lessonClassService;
    @Resource
    private ILessonClassStudentService lessonClassStudentService;

    @Resource
    private ISigninRecordService signinRecordService;

    @Resource
    private ISysUserService userService;

    @Resource
    private ILessonService lessonService;

    @Override
    public boolean addTask(SigninTaskCrateDTO signinTask) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime beginLocalTime = LocalTime.parse(signinTask.getBeginTime(), formatter);
        LocalTime endLocalTime = LocalTime.parse(signinTask.getEndTime(), formatter);
        if (endLocalTime.isBefore(beginLocalTime)) {
            throw new RuntimeException("结束时间不能早于开始时间");
        }
        // 转换为 LocalDateTime
        // 假设当前日期为 2024-02-16
        LocalDateTime beginDateTime = LocalDateTime.of(LocalDate.now(), beginLocalTime);
        LocalDateTime endDateTime = LocalDateTime.of(LocalDate.now(), endLocalTime);

        if (endDateTime.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("签到任务时间必须在当前时间之后");
        }

        SigninTask signinTask1 = BeanCopyUtils.copyBean(signinTask, SigninTask.class);
        signinTask1.setTeacherId(SecurityUtils.getUserId());
        signinTask1.setCreateTime(LocalDateTime.now());
        signinTask1.setBeginTime(beginDateTime);
        signinTask1.setEndTime(endDateTime);
        return this.save(signinTask1);
    }

    @Override
    public boolean monitorAddTask(SigninTaskCrateDTO signinTask) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime beginLocalTime = LocalTime.parse(signinTask.getBeginTime(), formatter);
        LocalTime endLocalTime = LocalTime.parse(signinTask.getEndTime(), formatter);
        if (endLocalTime.isBefore(beginLocalTime)) {
            throw new RuntimeException("结束时间不能早于开始时间");
        }
        // 转换为 LocalDateTime
        // 假设当前日期为 2024-02-16
        LocalDateTime beginDateTime = LocalDateTime.of(LocalDate.now(), beginLocalTime);
        LocalDateTime endDateTime = LocalDateTime.of(LocalDate.now(), endLocalTime);

        if (endDateTime.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("签到任务时间必须在当前时间之后");
        }

        SigninTask signinTask1 = BeanCopyUtils.copyBean(signinTask, SigninTask.class);
        // 获取classid对应教师id
        LessonClass lessonClass = lessonClassService.getById(signinTask.getClassId());
        Long lessonId = lessonClass.getLessonId();
        Lesson lesson = lessonService.getById(lessonId);
        Long teacherId = lesson.getTeacherId();
        signinTask1.setTeacherId(teacherId);
        signinTask1.setCreateTime(LocalDateTime.now());
        signinTask1.setBeginTime(beginDateTime);
        signinTask1.setEndTime(endDateTime);
        return this.save(signinTask1);
    }

    @Override
    public PageDataInfo taskPage(SigninTaskPageDTO signinTaskPageDTO) {
        LambdaQueryWrapper<SigninTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SigninTask::getTeacherId, SecurityUtils.getUserId()).eq(SigninTask::getClassId, signinTaskPageDTO.getClassId()).eq(SigninTask::getDelFlag, DelFlagEnum.OK.getCode()).orderByDesc(SigninTask::getCreateTime);
        Page<SigninTask> page = this.page(new Page<>(signinTaskPageDTO.getPageNum(), signinTaskPageDTO.getPageSize()), queryWrapper);
        List<SigninTask> records = page.getRecords();
        if (records.isEmpty()) {
            return PageDataInfo.emptyPage();
        }
        List<SigninTaskVO> signinTaskVOS = BeanCopyUtils.copyBeanList(records, SigninTaskVO.class);
        signinTaskVOS.forEach(vo -> {
            // 签到数量
            Long classStudentCount = lessonClassStudentService.getClassStudentCount(vo.getClassId());
            vo.setRealSigninCount(signinRecordService.getSigninCountByTaskId(vo.getTaskId()));
            vo.setWaitSigninCount(classStudentCount);
            // 签到结束
            vo.setSigninEnd(vo.getEndTime().isBefore(LocalDateTime.now()));
        });
        return PageDataInfo.genPageData(signinTaskVOS, page.getTotal());
    }

    @Override
    public PageDataInfo taskPage2(SigninTaskPageDTO signinTaskPageDTO) {
        // 获取classid对应教师id
        LessonClass lessonClass = lessonClassService.getById(signinTaskPageDTO.getClassId());
        Long lessonId = lessonClass.getLessonId();
        Lesson lesson = lessonService.getById(lessonId);
        Long teacherId = lesson.getTeacherId();
        LambdaQueryWrapper<SigninTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SigninTask::getTeacherId, teacherId)
                .eq(SigninTask::getClassId, signinTaskPageDTO.getClassId())
                .eq(SigninTask::getDelFlag, DelFlagEnum.OK.getCode())
                .orderByDesc(SigninTask::getCreateTime);
        Page<SigninTask> page = this.page(new Page<>(signinTaskPageDTO.getPageNum(), signinTaskPageDTO.getPageSize()), queryWrapper);
        List<SigninTask> records = page.getRecords();
        if (records.isEmpty()) {
            return PageDataInfo.emptyPage();
        }
        List<SigninTaskVO> signinTaskVOS = BeanCopyUtils.copyBeanList(records, SigninTaskVO.class);
        signinTaskVOS.forEach(vo -> {
            // 签到数量
            Long classStudentCount = lessonClassStudentService.getClassStudentCount(vo.getClassId());
            vo.setRealSigninCount(signinRecordService.getSigninCountByTaskId(vo.getTaskId()));
            vo.setWaitSigninCount(classStudentCount);
            // 签到结束
            vo.setSigninEnd(vo.getEndTime().isBefore(LocalDateTime.now()));
        });
        return PageDataInfo.genPageData(signinTaskVOS, page.getTotal());
    }

    @Override
    public List<SigninTaskVO> getWaitSignin() {
        List<Long> classIds = lessonClassStudentService.getClassIdsByStudentId(SecurityUtils.getUserId());
        if (classIds.isEmpty()) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<SigninTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ge(SigninTask::getEndTime, LocalDateTime.now()).in(SigninTask::getClassId, classIds).eq(SigninTask::getDelFlag, DelFlagEnum.OK.getCode()).orderByDesc(SigninTask::getCreateTime);

        List<SigninTask> list = this.list(queryWrapper);
        List<SigninTaskVO> signinTaskVOS = BeanCopyUtils.copyBeanList(list, SigninTaskVO.class);
        signinTaskVOS.forEach(vo -> {
            // 是否签到
            boolean b = signinRecordService.weatherSignin(vo.getTaskId(), SecurityUtils.getUserId());
            vo.setWeatherSignin(b);
        });
        return signinTaskVOS;
    }

    @Override
    public boolean taskWeatherExpire(Long taskId) {
        SigninTask byId = this.getById(taskId);
        if (byId.getEndTime().isBefore(LocalDateTime.now())) {
            return true;
        } else if (byId.getEndTime().isAfter(LocalDateTime.now()) && byId.getBeginTime().isBefore(LocalDateTime.now())) {
            return false;
        } else if (byId.getBeginTime().isAfter(LocalDateTime.now())) {
            return true;
        }
        return true;
    }

    @Override
    public List<SigninRecordVO> getSigninRecordList(Long taskId) {
        List<SigninRecord> signinRecords = signinRecordService.getsigninRecordList(taskId);
        List<SigninRecordVO> signinRecordVOS = BeanCopyUtils.copyBeanList(signinRecords, SigninRecordVO.class);
        signinRecordVOS.forEach(vo -> {
            // 查询签到用户
            SysUser sysUser = userService.selectUserById(vo.getStudentId());
            vo.setAvatar(sysUser.getAvatar());
            vo.setNickName(sysUser.getNickName());
        });
        return signinRecordVOS;
    }


}
