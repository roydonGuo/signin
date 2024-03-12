package com.roydon.business.app.domain.vo;

import com.roydon.business.app.domain.entity.SigninTask;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * SigninTaskVO
 *
 * @AUTHOR: roydon
 * @DATE: 2024/2/20
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class SigninTaskVO extends SigninTask {
    private boolean weatherSignin; // 是否签到
    private Long realSigninCount; // 实际签到数量
    private Long waitSigninCount; //实际需要
    private boolean signinEnd;
}
