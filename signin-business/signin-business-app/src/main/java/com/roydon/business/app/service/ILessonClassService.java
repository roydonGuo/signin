package com.roydon.business.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.roydon.business.app.domain.dto.LessonClassPageDTO;
import com.roydon.business.app.domain.dto.LessonClassSearchByCodePageDTO;
import com.roydon.business.app.domain.entity.LessonClass;
import com.roydon.business.app.domain.vo.PageDataInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (LessonClass)表服务接口
 *
 * @author roydon
 * @since 2024-01-20 21:30:17
 */
public interface ILessonClassService extends IService<LessonClass> {

    PageDataInfo getLessonClassPage(LessonClassPageDTO lessonClassPageDTO);

    /**
     * 检查课程唯一标识码是否存在
     *
     * @param code
     * @return
     */
    boolean checkUniqueCodeExists(String code);

    boolean saveLessonClass(LessonClass lessonClass);

    PageDataInfo searchByCode(LessonClassSearchByCodePageDTO pageDTO);

    boolean checkoutClassExist(Long classId);

    Long getClassCount(Long lessonId);
}
