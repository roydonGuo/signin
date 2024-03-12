package com.roydon.business.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roydon.business.app.domain.entity.LessonClassStudent;
import com.roydon.business.app.mapper.LessonClassStudentMapper;
import com.roydon.business.app.service.ILessonClassService;
import com.roydon.business.app.service.ILessonClassStudentService;
import com.roydon.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * (LessonClassStudent)表服务实现类
 *
 * @author roydon
 * @since 2024-02-16 19:16:33
 */
@Service("lessonClassStudentService")
public class LessonClassStudentServiceImpl extends ServiceImpl<LessonClassStudentMapper, LessonClassStudent> implements ILessonClassStudentService {
    @Resource
    private LessonClassStudentMapper lessonClassStudentMapper;

    @Resource
    private ILessonClassService lessonClassService;

    @Override
    public boolean enterClass(Long classId) {
        // 查询班级是否有效
        if (!lessonClassService.checkoutClassExist(classId)) {
            throw new RuntimeException("班级已删除");
        }
        // 查询学生是否已经加入该班级
        if (checkClassStudentExist(classId, SecurityUtils.getUserId())) {
            throw new RuntimeException("学生已经在该班级中");
        }
        LessonClassStudent lessonClassStudent = new LessonClassStudent();
        lessonClassStudent.setClassId(classId);
        lessonClassStudent.setStudentId(SecurityUtils.getUserId());
        lessonClassStudent.setCreateTime(LocalDateTime.now());
        return this.save(lessonClassStudent);
    }

    public boolean checkClassStudentExist(Long classId, Long studentId) {
        LambdaQueryWrapper<LessonClassStudent> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LessonClassStudent::getClassId, classId).eq(LessonClassStudent::getStudentId, studentId);
        return this.count(queryWrapper) > 0;
    }

    /**
     * 获取学生的班级ID列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<Long> getClassIdsByStudentId(Long userId) {
        LambdaQueryWrapper<LessonClassStudent> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LessonClassStudent::getStudentId, userId);
        List<LessonClassStudent> list = this.list(queryWrapper);
        return list.stream().map(LessonClassStudent::getClassId).collect(Collectors.toList());
    }

    @Override
    public Long getClassStudentCount(Long classId) {
        LambdaQueryWrapper<LessonClassStudent> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LessonClassStudent::getClassId, classId);
        return this.count(queryWrapper);
    }
}
