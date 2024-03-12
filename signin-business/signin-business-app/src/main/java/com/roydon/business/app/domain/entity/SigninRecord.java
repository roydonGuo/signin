package com.roydon.business.app.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.roydon.common.annotation.Excel;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * 签到记录表(SigninRecord)实体类
 *
 * @author roydon
 * @since 2024-02-19 16:11:10
 */
@Data
@TableName("signin_record")
public class SigninRecord implements Serializable {
    private static final long serialVersionUID = -42923245740672437L;
    /**
     * id
     */
    @TableId(value = "record_id", type = IdType.AUTO)
    @Excel(name = "记录id")
    private Long recordId;
    /**
     * 签到任务id
     */
    @NotNull
    @Excel(name = "任务id")
    private Long taskId;
    /**
     * 学生id
     */
    @Excel(name = "学生id")
    private Long studentId;
    /**
     * 学号
     */
    @NotNull(message = "学号不能为空")
    @Size(max = 12)
    @Excel(name = "学号")
    private String studentNumber;

    @NotNull(message = "姓名不能为空")
    @Size(max = 32)
    @Excel(name = "姓名")
    private String realName;

    @Excel(name = "签到地址")
    private String address;
    @Excel(name = "签到详细地址")
    private String addressDetail;

    @Excel(name = "签到照片")
    private String pictureUrl;

    @Excel(name = "签到时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;


}

