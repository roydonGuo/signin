package com.roydon.business.app.domain.vo;

import com.roydon.business.app.domain.entity.SigninRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * SigninRecordVO
 *
 * @AUTHOR: roydon
 * @DATE: 2024/2/19
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class SigninRecordVO extends SigninRecord {
    private String avatar;
    private String nickName;
}
