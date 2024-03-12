package com.roydon.business.app.controller;

import com.roydon.business.app.domain.dto.PageDTO;
import com.roydon.business.app.domain.entity.Lesson;
import com.roydon.business.app.domain.vo.PageDataInfo;
import com.roydon.business.app.service.ILessonService;
import com.roydon.common.core.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Lesson)表控制层
 *
 * @author roydon
 * @since 2024-01-20 20:52:33
 */
@Slf4j
@RestController
@RequestMapping("/app/lesson")
public class LessonController {

    @Resource
    private ILessonService lessonService;

    @PostMapping("/my-page")
    public PageDataInfo queryMyPage(@RequestBody PageDTO pageDTO) {
        return lessonService.myLessonPage(pageDTO);
    }

    /**
     * 通过主键查询单条数据
     */
    @GetMapping("{lessonId}")
    public AjaxResult queryById(@PathVariable("lessonId") Long lessonId) {
        return AjaxResult.success(this.lessonService.getLessonInfoById(lessonId));
    }

    /**
     * 新增数据
     *
     * @param lesson 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@Validated @RequestBody Lesson lesson) {
        return AjaxResult.success(this.lessonService.saveLesson(lesson));
    }

    /**
     * 编辑数据
     *
     * @param lesson 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody Lesson lesson) {
        return AjaxResult.success(this.lessonService.updateById(lesson));
    }

    /**
     * 删除数据
     */
    @DeleteMapping("/{lessonId}")
    public AjaxResult removeById(@PathVariable("lessonId") Long lessonId) {
        return AjaxResult.success(this.lessonService.removeById(lessonId));
    }

}

