package com.roydon.business.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.roydon.business.app.domain.entity.SigninRecord;

import java.util.List;

/**
 * 签到记录表(SigninRecord)表服务接口
 *
 * @author roydon
 * @since 2024-02-19 16:11:10
 */
public interface ISigninRecordService extends IService<SigninRecord> {

    boolean studentSignin(SigninRecord signinRecord);

    List<SigninRecord> getsigninRecordList(Long taskId);

    /**
     * 查询是否签到过
     * @param taskId
     * @param userId
     * @return
     */
    boolean weatherSignin(Long taskId,Long userId);

    /**
     * 获取签到任务已签到数量
     * @param taskId
     * @return
     */
    Long getSigninCountByTaskId(Long taskId);
}
