package com.roydon.business.app.domain.vo;

import com.roydon.business.app.domain.entity.LessonClass;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * LessonClassVO
 *
 * @AUTHOR: roydon
 * @DATE: 2024/2/19
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class LessonClassVO extends LessonClass {
    private Long studentCount;
}
