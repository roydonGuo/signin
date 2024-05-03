package com.roydon.business.app.domain.vo;

import com.roydon.business.app.domain.entity.LessonClassStudent;
import com.roydon.common.core.domain.entity.SysUser;
import lombok.Data;

/**
 * ClassStudentVO
 *
 * @AUTHOR: roydon
 * @DATE: 2024/5/3
 **/
@Data
public class ClassStudentVO extends LessonClassStudent {

    private SysUser user;
}
