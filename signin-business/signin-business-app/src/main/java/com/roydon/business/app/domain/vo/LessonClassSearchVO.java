package com.roydon.business.app.domain.vo;

import com.roydon.business.app.domain.entity.LessonClass;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * LessonClassSearchVO
 *
 * @AUTHOR: roydon
 * @DATE: 2024/2/16
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class LessonClassSearchVO extends LessonClass {
    private String teacherName;
    private String lessonName;
}
