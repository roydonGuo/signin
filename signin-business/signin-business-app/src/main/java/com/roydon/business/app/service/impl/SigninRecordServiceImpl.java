package com.roydon.business.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roydon.business.app.domain.entity.SigninRecord;
import com.roydon.business.app.mapper.SigninRecordMapper;
import com.roydon.business.app.service.ISigninRecordService;
import com.roydon.business.app.service.ISigninTaskService;
import com.roydon.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 签到记录表(SigninRecord)表服务实现类
 *
 * @author roydon
 * @since 2024-02-19 16:11:10
 */
@Service("signinRecordService")
public class SigninRecordServiceImpl extends ServiceImpl<SigninRecordMapper, SigninRecord> implements ISigninRecordService {

    @Resource
    private SigninRecordMapper signinRecordMapper;

    @Resource
    private ISigninTaskService signinTaskService;

    @Override
    public boolean studentSignin(SigninRecord signinRecord) {
        // 查询签到任务是否过期
        boolean expire = signinTaskService.taskWeatherExpire(signinRecord.getTaskId());
        if (expire) {
            throw new RuntimeException("不在签到时间内");
        }
        // 查询是否签到过
        LambdaQueryWrapper<SigninRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SigninRecord::getTaskId, signinRecord.getTaskId())
                .eq(SigninRecord::getStudentId, SecurityUtils.getUserId());
        if (this.count(queryWrapper) > 0) {
            throw new RuntimeException("已签到");
        }
        signinRecord.setStudentId(SecurityUtils.getUserId());
        signinRecord.setCreateTime(LocalDateTime.now());

        return this.save(signinRecord);
    }

    @Override
    public List<SigninRecord> getsigninRecordList(Long taskId) {
        LambdaQueryWrapper<SigninRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SigninRecord::getTaskId, taskId);
        return this.list(queryWrapper);
    }

    /**
     * 查询是否签到过
     *
     * @param taskId
     * @param userId
     * @return
     */
    @Override
    public boolean weatherSignin(Long taskId, Long userId) {
        LambdaQueryWrapper<SigninRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SigninRecord::getTaskId, taskId)
                .eq(SigninRecord::getStudentId, userId);
        return this.count(queryWrapper) > 0;
    }

    /**
     * 获取签到任务已签到数量
     *
     * @param taskId
     * @return
     */
    @Override
    public Long getSigninCountByTaskId(Long taskId) {
        LambdaQueryWrapper<SigninRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SigninRecord::getTaskId, taskId);
        return this.count(queryWrapper);
    }
}
