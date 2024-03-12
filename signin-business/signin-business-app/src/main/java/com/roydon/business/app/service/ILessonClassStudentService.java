package com.roydon.business.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.roydon.business.app.domain.entity.LessonClassStudent;

import java.util.List;

/**
 * (LessonClassStudent)表服务接口
 *
 * @author roydon
 * @since 2024-02-16 19:16:33
 */
public interface ILessonClassStudentService extends IService<LessonClassStudent> {


    boolean enterClass(Long classId);

    /**
     * 获取学生的班级ID列表
     * @param userId
     * @return
     */
    List<Long> getClassIdsByStudentId(Long userId);

    Long getClassStudentCount(Long classId);
}
