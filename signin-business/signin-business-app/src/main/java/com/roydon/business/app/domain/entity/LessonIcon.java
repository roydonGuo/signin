package com.roydon.business.app.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * (LessonIcon)实体类
 *
 * @author roydon
 * @since 2024-01-20 22:49:24
 */
@Data
@TableName("lesson_icon")
public class LessonIcon implements Serializable {
    private static final long serialVersionUID = 875059533527082748L;
    /**
     * 图标id
     */
    @TableId(value = "icon_id", type = IdType.AUTO)
    private Long iconId;
    /**
     * 地址
     */
    private String remoteUrl;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;


}

