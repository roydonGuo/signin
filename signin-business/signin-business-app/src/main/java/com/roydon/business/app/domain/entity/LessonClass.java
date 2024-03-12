package com.roydon.business.app.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * (LessonClass)实体类
 *
 * @author roydon
 * @since 2024-01-20 21:30:16
 */
@Data
@TableName("lesson_class")
public class LessonClass implements Serializable {
    private static final long serialVersionUID = -27619529824646492L;
    /**
     * id
     */
    @TableId(value = "class_id", type = IdType.AUTO)
    private Long classId;
    /**
     * 课程id
     */
    @NotNull(message = "不能为空")
    private Long lessonId;
    /**
     * 班级名称
     */
    @Size(min = 6, max = 32, message = "班级名称长度需在6-32字符")
    private String className;

    private String uniqueCode;
    private String delFlag;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;


}

