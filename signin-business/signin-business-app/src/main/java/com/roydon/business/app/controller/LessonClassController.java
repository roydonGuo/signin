package com.roydon.business.app.controller;

import com.roydon.business.app.domain.dto.LessonClassPageDTO;
import com.roydon.business.app.domain.dto.LessonClassSearchByCodePageDTO;
import com.roydon.business.app.domain.dto.PageDTO;
import com.roydon.business.app.domain.entity.LessonClass;
import com.roydon.business.app.domain.vo.PageDataInfo;
import com.roydon.business.app.service.ILessonClassService;
import com.roydon.common.core.domain.AjaxResult;
import com.roydon.common.utils.uuid.IdUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * (LessonClass)表控制层
 *
 * @author roydon
 * @since 2024-01-20 21:30:16
 */
@RestController
@RequestMapping("/app/lessonClass")
public class LessonClassController {

    @Resource
    private ILessonClassService lessonClassService;

    @PostMapping("/page")
    public PageDataInfo getClassPage(@Validated @RequestBody LessonClassPageDTO lessonClassPageDTO) {
        return lessonClassService.getLessonClassPage(lessonClassPageDTO);
    }

    /**
     * 新增数据
     *
     * @param lessonClass 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@Validated @RequestBody LessonClass lessonClass) {
        return AjaxResult.success(this.lessonClassService.saveLessonClass(lessonClass));
    }

    /**
     * 编辑数据
     *
     * @param lessonClass 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody LessonClass lessonClass) {
        return AjaxResult.success(this.lessonClassService.updateById(lessonClass));
    }

    /**
     * 删除数据
     *
     * @param classId 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{classId}")
    public AjaxResult removeById(@PathVariable("classId") Long classId) {
        return AjaxResult.success(this.lessonClassService.removeById(classId));
    }

    @PostMapping("/searchByCode")
    public PageDataInfo searchByCode(@Validated @RequestBody LessonClassSearchByCodePageDTO pageDTO) {
        return lessonClassService.searchByCode(pageDTO);
    }

}

