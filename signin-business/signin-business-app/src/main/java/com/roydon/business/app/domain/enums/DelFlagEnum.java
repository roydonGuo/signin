package com.roydon.business.app.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DelFlagEnum
 *
 * @AUTHOR: roydon
 * @DATE: 2024/2/16
 **/
@Getter
@AllArgsConstructor
public enum DelFlagEnum {
    OK("0", "正常"),
    DEL("1", "删除");

    private final String code;
    private final String info;

}
