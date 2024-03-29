package com.roydon.sms.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.roydon.common.constant.CacheConstants;
import com.roydon.common.exception.alisms.SmsException;
import com.roydon.common.utils.StringUtils;
import com.roydon.sms.domain.model.AliSmsResponse;
import com.roydon.sms.domain.model.SmsCode;
import com.roydon.sms.service.AliyunSmsService;
import com.roydon.sms.service.ISmsProviderService;
import com.roydon.sms.util.AliyunSmsUtil;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * AliyunSmsServiceImpl
 *
 * @AUTHOR: roydon
 * @DATE: 2023/4/27
 **/
@Service
public class AliyunSmsServiceImpl implements AliyunSmsService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private ISmsProviderService smsProviderService;

    private static final long SMSCODE_EXPIRE_TIME = 1l;

    @Override
    public SmsCode sendCode(String phone) {
        // 存入redis的验证码key
        String captchaKey = CacheConstants.ALIYUN_SMS_LOGIN_KEY + phone;
        // 根据手机号从redis中拿验证码
        String phoneCode = redisTemplate.opsForValue().get(captchaKey);
        if (!StringUtils.isEmpty(phoneCode)) {
            throw new SmsException("请勿重复发送-" + phone);
        }
        // 生成随机验证码
        String smsCode = RandomStringUtils.randomNumeric(6);

        String callbackMsg = AliyunSmsUtil.sendSms(phone, 8, smsCode);
        // 回调消息转换
        JSONObject jsonObject = JSON.parseObject(callbackMsg);
        AliSmsResponse aliSmsResponse = JSON.toJavaObject(jsonObject, AliSmsResponse.class);
        System.out.println(aliSmsResponse.toString());
        // 调用短信服务失败
        if (!aliSmsResponse.getCode().equals(AliyunSmsUtil.MESSAGE_OK)) {
            throw new SmsException(aliSmsResponse.getMessage());
        }
        // 将验证码存到 redis ，默认一分钟
        redisTemplate.opsForValue().set(captchaKey, smsCode, SMSCODE_EXPIRE_TIME, TimeUnit.MINUTES);
        // TODO 动态扣减短信套餐余量,目前默认扣减aliyun的sms
        smsProviderService.decrementResidueCountByProviderId("1111040954007027712fbea48bbbc4841eda93c0ed1f3630bc8");
        SmsCode sCode = new SmsCode(smsCode, SMSCODE_EXPIRE_TIME);
        return sCode;
    }

    @Override
    public SmsCode sendRegisterCode(String phone) {
        // 存入redis的验证码key
        String captchaKey = CacheConstants.ALIYUN_SMS_REGISTER_KEY + phone;
        // 根据手机号从redis中拿验证码
        String phoneCode = redisTemplate.opsForValue().get(captchaKey);
        if (!StringUtils.isEmpty(phoneCode)) {
            throw new SmsException("请勿重复发送-" + phone);
        }
        // 生成随机验证码
        String smsCode = RandomStringUtils.randomNumeric(6);

        String callbackMsg = AliyunSmsUtil.sendSms(phone, 8, smsCode);
        // 回调消息转换
        JSONObject jsonObject = JSON.parseObject(callbackMsg);
        AliSmsResponse aliSmsResponse = JSON.toJavaObject(jsonObject, AliSmsResponse.class);
        System.out.println(aliSmsResponse.toString());
        // 调用短信服务失败
        if (!aliSmsResponse.getCode().equals(AliyunSmsUtil.MESSAGE_OK)) {
            throw new SmsException(aliSmsResponse.getMessage());
        }
        // 将验证码存到 redis ，默认一分钟
        redisTemplate.opsForValue().set(captchaKey, smsCode, SMSCODE_EXPIRE_TIME, TimeUnit.MINUTES);
        // TODO 动态扣减短信套餐余量,目前默认扣减aliyun的sms
        smsProviderService.decrementResidueCountByProviderId("1111040954007027712fbea48bbbc4841eda93c0ed1f3630bc8");
        SmsCode sCode = new SmsCode(smsCode, SMSCODE_EXPIRE_TIME);
        return sCode;
    }
}
