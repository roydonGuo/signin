package com.roydon.business.app.domain.vo;

import com.roydon.business.app.domain.entity.Lesson;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * LessonVO
 *
 * @AUTHOR: roydon
 * @DATE: 2024/2/19
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class LessonVO extends Lesson {
    // 班级数量
    private Long classCount;
}
