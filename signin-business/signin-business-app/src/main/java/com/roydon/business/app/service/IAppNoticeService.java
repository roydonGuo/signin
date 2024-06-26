package com.roydon.business.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.roydon.business.app.domain.entity.AppNotice;

import java.util.List;

/**
 * app端图文轮播图公告(AppNotice)表服务接口
 *
 * @author roydon
 * @since 2023-06-13 22:10:43
 */
public interface IAppNoticeService extends IService<AppNotice> {

    /**
     * 查询app端图文轮播图公告
     *
     * @param noticeId app端图文轮播图公告主键
     * @return app端图文轮播图公告
     */
    AppNotice selectAppNoticeByNoticeId(Long noticeId);

    /**
     * 查询app端图文轮播图公告列表
     *
     * @param appNotice app端图文轮播图公告
     * @return app端图文轮播图公告集合
     */
    List<AppNotice> getAppNoticeList(AppNotice appNotice);

    /**
     * 查询app端图文轮播图公告列表
     *
     * @param appNotice app端图文轮播图公告
     * @return app端图文轮播图公告集合
     */
    List<AppNotice> getAppBanner(AppNotice appNotice);

    /**
     * 新增app端图文轮播图公告
     *
     * @param appNotice app端图文轮播图公告
     * @return 结果
     */
    int insertAppNotice(AppNotice appNotice);

    /**
     * 修改app端图文轮播图公告
     *
     * @param appNotice app端图文轮播图公告
     * @return 结果
     */
    int updateAppNotice(AppNotice appNotice);

    /**
     * 改变状态
     *
     * @param appNotice
     * @return
     */
    boolean changeStatus(AppNotice appNotice);

    /**
     * 删除app端图文轮播图公告
     *
     * @param noticeId app端图文轮播图公告主键
     * @return 结果
     */
    int deleteAppNoticeByNoticeId(Long noticeId);

    /**
     * 批量删除app端图文轮播图公告
     *
     * @param noticeIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteAppNoticeByNoticeIds(Long[] noticeIds);

}
