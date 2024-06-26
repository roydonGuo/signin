package com.roydon.business.app.controller;

import com.roydon.business.app.domain.entity.LessonClassStudent;
import com.roydon.business.app.service.ILessonClassStudentService;
import com.roydon.common.core.domain.AjaxResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (LessonClassStudent)表控制层
 *
 * @author roydon
 * @since 2024-02-16 19:16:33
 */
@RestController
@RequestMapping("/app/lessonClassStudent")
public class LessonClassStudentController {

    @Resource
    private ILessonClassStudentService lessonClassStudentService;

    @GetMapping("/{classId}")
    public AjaxResult enterClass(@PathVariable("classId") Long classId) {
        return AjaxResult.success(lessonClassStudentService.enterClass(classId));
    }

    /**
     * 课程班级下学生列表
     */
    @GetMapping("/studentList/{classId}")
    public AjaxResult studentList(@PathVariable("classId") Long classId) {
        return AjaxResult.success(lessonClassStudentService.studentList(classId));
    }

    /**
     * 授权班长
     */
    @GetMapping("/authMonitor/{classId}/{userId}")
    public AjaxResult authMonitor(@PathVariable("classId") Long classId,@PathVariable("userId") Long userId) {
        return AjaxResult.success(lessonClassStudentService.authMonitor(classId,userId));
    }

}

