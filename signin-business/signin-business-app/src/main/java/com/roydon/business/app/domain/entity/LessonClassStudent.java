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
 * (LessonClassStudent)实体类
 *
 * @author roydon
 * @since 2024-02-16 19:16:33
 */
@Data
@TableName("lesson_class_student")
public class LessonClassStudent implements Serializable {
    private static final long serialVersionUID = -69529803297027710L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 课程班级id
     */
    @NotNull
    private Long classId;
    /**
     * 学生id
     */
    private Long studentId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}

