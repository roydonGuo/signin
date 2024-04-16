package com.roydon.business.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.roydon.business.app.domain.dto.PageDTO;
import com.roydon.business.app.domain.entity.Lesson;
import com.roydon.business.app.domain.vo.LessonVO;
import com.roydon.business.app.domain.vo.PageDataInfo;

import java.util.List;

/**
 * (Lesson)表服务接口
 *
 * @author roydon
 * @since 2024-01-20 20:52:36
 */
public interface ILessonService extends IService<Lesson> {

    /**
     * 保存课程
     *
     * @param lesson
     * @return
     */
    Long saveLesson(Lesson lesson);

    PageDataInfo myLessonPage(PageDTO pageDTO);

    LessonVO getLessonInfoById(Long lessonId);

    List<Lesson> selectLessonList(Lesson lesson);
}
