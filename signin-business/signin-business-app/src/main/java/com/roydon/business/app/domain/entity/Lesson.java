package com.roydon.business.app.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * (Lesson)实体类
 *
 * @author roydon
 * @since 2024-01-20 20:52:35
 */
@Data
@TableName("lesson")
public class Lesson implements Serializable {
    private static final long serialVersionUID = 319221654910094299L;
    /**
     * 课程id
     */
    @TableId(value = "lesson_id", type = IdType.AUTO)
    private Long lessonId;
    /**
     * 教师id
     */
    private Long teacherId;
    /**
     * 课程名称
     */
    @NotBlank(message = "不允许为空")
    private String lessonName;
    private String cover;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    /**
     * 描述
     */
    private String lessonDesc;


}

