package com.roydon.business.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.roydon.business.app.domain.entity.LessonIcon;
import com.roydon.business.app.domain.vo.PageDataInfo;

import java.util.List;

/**
 * (LessonIcon)表服务接口
 *
 * @author roydon
 * @since 2024-01-20 22:49:25
 */
public interface ILessonIconService extends IService<LessonIcon> {

    List<LessonIcon> selectLessonIconList(LessonIcon lessonIcon);

    LessonIcon selectLessonIconByIconId(Long iconId);

    PageDataInfo getAllIcon();
}
