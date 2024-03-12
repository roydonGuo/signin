package com.roydon.business.app.domain.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 签到任务表(SigninTask)实体类
 *
 * @author roydon
 * @since 2024-02-16 16:43:48
 */
@Data
public class SigninTaskCrateDTO {

    /**
     * 班级id
     */
    @NotNull
    private Long classId;
    /**
     * 名称
     */
    private String title;

    /**
     * 开始时间
     */
    @NotNull
    private String beginTime;
    /**
     * 结束时间
     */
    @NotNull
    private String endTime;
    /**
     * 签到类型
     */
    private String signinType;

}

