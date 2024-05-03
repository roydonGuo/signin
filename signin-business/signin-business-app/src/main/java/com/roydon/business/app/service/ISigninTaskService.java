package com.roydon.business.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.roydon.business.app.domain.dto.SigninTaskCrateDTO;
import com.roydon.business.app.domain.dto.SigninTaskPageDTO;
import com.roydon.business.app.domain.entity.SigninRecord;
import com.roydon.business.app.domain.entity.SigninTask;
import com.roydon.business.app.domain.vo.PageDataInfo;
import com.roydon.business.app.domain.vo.SigninRecordVO;
import com.roydon.business.app.domain.vo.SigninTaskVO;

import java.util.List;

/**
 * 签到任务表(SigninTask)表服务接口
 *
 * @author roydon
 * @since 2024-02-16 16:43:48
 */
public interface ISigninTaskService extends IService<SigninTask> {

    boolean addTask(SigninTaskCrateDTO signinTask);

    boolean monitorAddTask(SigninTaskCrateDTO signinTask);
    PageDataInfo taskPage(SigninTaskPageDTO signinTaskPageDTO);

    /**
     * 获取待签到任务
     *
     * @return
     */
    List<SigninTaskVO> getWaitSignin();

    /**
     * 任务是否过期 ：
     * false未过期，
     * true已过期
     *
     * @param taskId
     * @return
     */
    boolean taskWeatherExpire(Long taskId);

    List<SigninRecordVO> getSigninRecordList(Long taskId);


    PageDataInfo taskPage2(SigninTaskPageDTO signinTaskPageDTO);

}
