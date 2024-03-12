package com.roydon.business.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roydon.business.app.domain.entity.Lesson;
import com.roydon.business.app.domain.entity.LessonIcon;
import com.roydon.business.app.domain.vo.LessonIconSelectVO;
import com.roydon.business.app.domain.vo.PageDataInfo;
import com.roydon.business.app.mapper.LessonIconMapper;
import com.roydon.business.app.service.ILessonIconService;
import com.roydon.common.utils.SecurityUtils;
import com.roydon.common.utils.bean.BeanCopyUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (LessonIcon)表服务实现类
 *
 * @author roydon
 * @since 2024-01-20 22:49:25
 */
@Service("lessonIconService")
public class LessonIconServiceImpl extends ServiceImpl<LessonIconMapper, LessonIcon> implements ILessonIconService {
    @Resource
    private LessonIconMapper lessonIconMapper;

    @Override
    public PageDataInfo getAllIcon() {
        List<LessonIcon> list = this.list();
        List<LessonIconSelectVO> lessonIconSelectVOS = BeanCopyUtils.copyBeanList(list, LessonIconSelectVO.class);
        return PageDataInfo.genPageData(lessonIconSelectVOS, list.size());

    }

    @Override
    public List<LessonIcon> selectLessonIconList(LessonIcon lessonIcon) {
        return lessonIconMapper.selectLessonIconList(lessonIcon);
    }

    @Override
    public LessonIcon selectLessonIconByIconId(Long iconId) {
        return lessonIconMapper.selectLessonIconByIconId(iconId);
    }
}
