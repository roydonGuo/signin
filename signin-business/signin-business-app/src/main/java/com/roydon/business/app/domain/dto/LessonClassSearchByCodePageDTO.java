package com.roydon.business.app.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * LessonClassPageDTO
 *
 * @AUTHOR: roydon
 * @DATE: 2024/2/16
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class LessonClassSearchByCodePageDTO extends PageDTO {
    // 课程标识
    @NotNull
    @NotBlank
    private String uniqueCode;
}
