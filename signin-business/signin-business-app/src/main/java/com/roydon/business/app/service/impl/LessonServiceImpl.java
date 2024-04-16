package com.roydon.business.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roydon.business.app.domain.dto.PageDTO;
import com.roydon.business.app.domain.entity.Lesson;
import com.roydon.business.app.domain.vo.LessonVO;
import com.roydon.business.app.domain.vo.PageDataInfo;
import com.roydon.business.app.mapper.LessonMapper;
import com.roydon.business.app.service.ILessonClassService;
import com.roydon.business.app.service.ILessonService;
import com.roydon.common.utils.DateUtils;
import com.roydon.common.utils.SecurityUtils;
import com.roydon.common.utils.StringUtil;
import com.roydon.common.utils.bean.BeanCopyUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * (Lesson)表服务实现类
 *
 * @author roydon
 * @since 2024-01-20 20:52:36
 */
@Service("lessonService")
public class LessonServiceImpl extends ServiceImpl<LessonMapper, Lesson> implements ILessonService {

    @Resource
    private ILessonClassService lessonClassService;

    @Resource
    private LessonMapper lessonMapper;

    /**
     * 保存课程
     *
     * @param lesson
     * @return
     */
    @Override
    public Long saveLesson(Lesson lesson) {
        // 判断课程是否存在
        if (countByLessonName(lesson.getLessonName())) {
            throw new RuntimeException("课程已存在");
        }
        lesson.setTeacherId(SecurityUtils.getUserId());
        lesson.setCreateTime(LocalDateTime.now());
        this.save(lesson);
        return lesson.getLessonId();
    }

    @Override
    public PageDataInfo myLessonPage(PageDTO pageDTO) {
        LambdaQueryWrapper<Lesson> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Lesson::getTeacherId, SecurityUtils.getUserId());
        queryWrapper.orderByDesc(Lesson::getCreateTime);
        Page<Lesson> page = this.page(new Page<>(pageDTO.getPageNum(), pageDTO.getPageSize()), queryWrapper);
        // 封装vo
        List<Lesson> records = page.getRecords();
        if (records.isEmpty()) {
            return PageDataInfo.emptyPage();
        }
        List<LessonVO> lessonVOS = BeanCopyUtils.copyBeanList(records, LessonVO.class);
        lessonVOS.forEach(vo -> {
            // 获取班级数量
            vo.setClassCount(lessonClassService.getClassCount(vo.getLessonId()));
        });
        return PageDataInfo.genPageData(lessonVOS, page.getTotal());
    }

    private boolean countByLessonName(String lessonName) {
        LambdaQueryWrapper<Lesson> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Lesson::getLessonName, lessonName);
        return this.count(queryWrapper) > 0;
    }

    @Override
    public LessonVO getLessonInfoById(Long lessonId) {
        Lesson lesson = this.getById(lessonId);
        LessonVO lessonVO = BeanCopyUtils.copyBean(lesson, LessonVO.class);
        lessonVO.setClassCount(lessonClassService.getClassCount(lessonId));
        return lessonVO;
    }

    @Override
    public List<Lesson> selectLessonList(Lesson lesson) {
        LambdaQueryWrapper<Lesson> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtil.isNotEmpty(lesson.getTeacherId()), Lesson::getTeacherId, lesson.getTeacherId());
        queryWrapper.like(StringUtil.isNotEmpty(lesson.getLessonName()), Lesson::getLessonName, lesson.getLessonName());
        return this.list(queryWrapper);
    }
}
