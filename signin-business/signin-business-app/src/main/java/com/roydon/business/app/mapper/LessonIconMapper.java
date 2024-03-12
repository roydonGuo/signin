package com.roydon.business.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roydon.business.app.domain.entity.LessonIcon;

import java.util.List;

/**
 * (LessonIcon)表数据库访问层
 *
 * @author roydon
 * @since 2024-01-20 22:49:24
 */
public interface LessonIconMapper extends BaseMapper<LessonIcon>{
    /**
     * 查询【请填写功能名称】
     *
     * @param iconId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    LessonIcon selectLessonIconByIconId(Long iconId);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param lessonIcon 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    List<LessonIcon> selectLessonIconList(LessonIcon lessonIcon);

    /**
     * 新增【请填写功能名称】
     *
     * @param lessonIcon 【请填写功能名称】
     * @return 结果
     */
    int insertLessonIcon(LessonIcon lessonIcon);

    /**
     * 修改【请填写功能名称】
     *
     * @param lessonIcon 【请填写功能名称】
     * @return 结果
     */
    int updateLessonIcon(LessonIcon lessonIcon);

    /**
     * 删除【请填写功能名称】
     *
     * @param iconId 【请填写功能名称】主键
     * @return 结果
     */
    int deleteLessonIconByIconId(Long iconId);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param iconIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteLessonIconByIconIds(Long[] iconIds);
}

