package com.roydon.business.app.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * 签到任务表(SigninTask)实体类
 *
 * @author roydon
 * @since 2024-02-16 16:43:48
 */
@Data
@TableName("signin_task")
public class SigninTask implements Serializable {
    private static final long serialVersionUID = -16152385168999400L;
    /**
     * id
     */
    @TableId(value = "task_id", type = IdType.AUTO)
    private Long taskId;
    /**
     * 教师id
     */
    private Long teacherId;
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
     * 签到类型0默认1定位2拍照
     */
    private String signinType;
    /**
     * 删除标记0默认1删除
     */
    private String delFlag;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /**
     * 开始时间
     */
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginTime;
    /**
     * 结束时间
     */
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;


}

