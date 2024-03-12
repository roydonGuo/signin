package com.roydon.business.app.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * LessonClassPageDTO
 *
 * @AUTHOR: roydon
 * @DATE: 2024/2/16
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class LessonClassPageDTO extends PageDTO {
    // 课程id
    @NotNull
    private Long lessonId;
}
