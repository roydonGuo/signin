package com.roydon.business.app.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * SigninTaskPageDTO
 *
 * @AUTHOR: roydon
 * @DATE: 2024/2/16
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class SigninTaskPageDTO extends PageDTO {
    private Long classId;
}
