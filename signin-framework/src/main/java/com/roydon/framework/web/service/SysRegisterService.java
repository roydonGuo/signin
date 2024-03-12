package com.roydon.framework.web.service;

import com.roydon.common.constant.CacheConstants;
import com.roydon.common.constant.Constants;
import com.roydon.common.constant.UserConstants;
import com.roydon.common.core.domain.entity.SysUser;
import com.roydon.common.core.domain.model.RegisterBody;
import com.roydon.common.core.redis.RedisCache;
import com.roydon.common.exception.user.CaptchaException;
import com.roydon.common.exception.user.CaptchaExpireException;
import com.roydon.common.utils.MessageUtils;
import com.roydon.common.utils.SecurityUtils;
import com.roydon.common.utils.StringUtils;
import com.roydon.framework.manager.AsyncManager;
import com.roydon.framework.manager.factory.AsyncFactory;
import com.roydon.system.service.ISysConfigService;
import com.roydon.system.service.ISysRoleService;
import com.roydon.system.service.ISysUserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 注册校验方法
 */
@Component
public class SysRegisterService {
    @Resource
    private ISysUserService userService;

    @Resource
    private ISysConfigService configService;

    @Resource
    private RedisCache redisCache;

    @Resource
    private ISysRoleService roleService;

    /**
     * 注册
     */
    public String register(RegisterBody registerBody) {
        String msg = "", username = registerBody.getUsername(), password = registerBody.getPassword();
        SysUser sysUser = new SysUser();
        sysUser.setUserName(username);

        // 验证码开关
        boolean captchaEnabled = configService.selectCaptchaEnabled();
        if (captchaEnabled) {
            validateCaptcha(username, registerBody.getCode(), registerBody.getUuid());
        }

        if (StringUtils.isEmpty(username)) {
            msg = "用户名不能为空";
        } else if (StringUtils.isEmpty(password)) {
            msg = "用户密码不能为空";
        } else if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            msg = "账户长度必须在2到20个字符之间";
        } else if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            msg = "密码长度必须在5到20个字符之间";
        } else if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(sysUser))) {
            msg = "保存用户'" + username + "'失败，注册账号已存在";
        } else {
            sysUser.setNickName(username);
            sysUser.setPassword(SecurityUtils.encryptPassword(password));
            Long userReturnId = userService.saveUserReturnId(sysUser);
            if (StringUtils.isNull(userReturnId)) {
                msg = "注册失败,请联系系统管理人员";
            } else {
                // 注册成功，绑定角色
                roleService.insertAuthUsers(registerBody.getRoleId(), new Long[]{userReturnId});
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.REGISTER, MessageUtils.message("user.register.success")));
            }
        }
        return msg;
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public void validateCaptcha(String username, String code, String uuid) {
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null) {
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha)) {
            throw new CaptchaException();
        }
    }
}
