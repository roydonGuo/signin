package com.roydon.common.core.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户注册对象
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RegisterBody extends LoginBody {

    /**
     * 注册角色 2教师3学生
     */
    private Long roleId = 3L;

}
