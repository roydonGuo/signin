package com.roydon.business.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roydon.business.app.domain.dto.LessonClassPageDTO;
import com.roydon.business.app.domain.dto.LessonClassSearchByCodePageDTO;
import com.roydon.business.app.domain.entity.Lesson;
import com.roydon.business.app.domain.entity.LessonClass;
import com.roydon.business.app.domain.enums.DelFlagEnum;
import com.roydon.business.app.domain.vo.LessonClassSearchVO;
import com.roydon.business.app.domain.vo.LessonClassVO;
import com.roydon.business.app.domain.vo.PageDataInfo;
import com.roydon.business.app.mapper.LessonClassMapper;
import com.roydon.business.app.service.ILessonClassService;
import com.roydon.business.app.service.ILessonClassStudentService;
import com.roydon.business.app.service.ILessonService;
import com.roydon.common.core.domain.entity.SysUser;
import com.roydon.common.utils.bean.BeanCopyUtils;
import com.roydon.common.utils.uuid.IdUtils;
import com.roydon.system.service.ISysUserService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * (LessonClass)表服务实现类
 *
 * @author roydon
 * @since 2024-01-20 21:30:17
 */
@Service("lessonClassService")
public class LessonClassServiceImpl extends ServiceImpl<LessonClassMapper, LessonClass> implements ILessonClassService {
    @Resource
    private LessonClassMapper lessonClassMapper;

    @Resource
    private ISysUserService userService;

    @Resource
    ILessonService lessonService;

    @Resource
    ILessonClassStudentService lessonClassStudentService;

    @Override
    public PageDataInfo getLessonClassPage(LessonClassPageDTO lessonClassPageDTO) {
        LambdaQueryWrapper<LessonClass> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LessonClass::getLessonId, lessonClassPageDTO.getLessonId());
        queryWrapper.eq(LessonClass::getDelFlag, DelFlagEnum.OK.getCode());
        Page<LessonClass> page = this.page(new Page<>(lessonClassPageDTO.getPageNum(), lessonClassPageDTO.getPageSize()), queryWrapper);
        List<LessonClass> records = page.getRecords();
        if (records.isEmpty()) {
            return PageDataInfo.emptyPage();
        }
        List<LessonClassVO> lessonClassVOS = BeanCopyUtils.copyBeanList(records, LessonClassVO.class);
        lessonClassVOS.forEach(vo -> {
            // 查询学生数
            vo.setStudentCount(lessonClassStudentService.getClassStudentCount(vo.getClassId()));
        });
        return PageDataInfo.genPageData(lessonClassVOS, page.getTotal());
    }

    @Override
    public boolean saveLessonClass(LessonClass lessonClass) {
        lessonClass.setCreateTime(LocalDateTime.now());
        String uuid = IdUtils.shortUUID();
        while (checkUniqueCodeExists(uuid)) {
            uuid = IdUtils.shortUUID();
        }
        lessonClass.setUniqueCode(uuid);
        return this.save(lessonClass);
    }

    /**
     * 检查课程唯一标识码是否存在
     *
     * @param code
     * @return
     */
    @Override
    public boolean checkUniqueCodeExists(String code) {
        LambdaQueryWrapper<LessonClass> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LessonClass::getUniqueCode, code);
        return this.count(queryWrapper) > 0;
    }

    @Override
    public PageDataInfo searchByCode(LessonClassSearchByCodePageDTO pageDTO) {
        LambdaQueryWrapper<LessonClass> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(LessonClass::getUniqueCode, pageDTO.getUniqueCode());
        queryWrapper.eq(LessonClass::getDelFlag, DelFlagEnum.OK.getCode())
                .orderByDesc(LessonClass::getCreateTime);
        Page<LessonClass> page = this.page(new Page<>(pageDTO.getPageNum(), pageDTO.getPageSize()), queryWrapper);
        List<LessonClass> records = page.getRecords();
        if (records.isEmpty()) {
            return PageDataInfo.emptyPage();
        }
        List<LessonClassSearchVO> lessonClassSearchVOS = BeanCopyUtils.copyBeanList(records, LessonClassSearchVO.class);
        // 课程名称、任课老师
        lessonClassSearchVOS.forEach(vo -> {
            Lesson lesson = lessonService.getById(vo.getLessonId());
            vo.setLessonName(lesson.getLessonName());
            SysUser sysUser = userService.selectUserById(lesson.getTeacherId());
            vo.setTeacherName(sysUser.getNickName());
        });
        return PageDataInfo.genPageData(lessonClassSearchVOS, page.getTotal());
    }

    @Override
    public boolean checkoutClassExist(Long classId) {
        LambdaQueryWrapper<LessonClass> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LessonClass::getDelFlag, DelFlagEnum.OK.getCode());
        queryWrapper.eq(LessonClass::getClassId, classId);
        return this.count(queryWrapper) > 0;
    }

    @Override
    public Long getClassCount(Long lessonId) {
        LambdaQueryWrapper<LessonClass> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LessonClass::getDelFlag, DelFlagEnum.OK.getCode());
        queryWrapper.eq(LessonClass::getLessonId, lessonId);
        return this.count(queryWrapper);
    }
}
