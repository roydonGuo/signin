package com.roydon.business.app.controller;

import com.roydon.business.app.domain.entity.SigninRecord;
import com.roydon.business.app.service.ISigninRecordService;
import com.roydon.business.oss.service.OssService;
import com.roydon.common.annotation.Log;
import com.roydon.common.core.domain.AjaxResult;
import com.roydon.common.core.domain.entity.SysUser;
import com.roydon.common.enums.BusinessType;
import com.roydon.common.utils.encrypt.IdCardNumUtil;
import com.roydon.common.utils.encrypt.TelephoneUtil;
import com.roydon.common.utils.poi.ExcelUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 签到记录表(SigninRecord)表控制层
 *
 * @author roydon
 * @since 2024-02-19 16:11:09
 */
@RestController
@RequestMapping("/app/signinRecord")
public class SigninRecordController {

    @Resource
    private ISigninRecordService signinRecordService;

    @Resource
    private OssService ossService;

    /**
     * 学生端签到
     *
     * @param signinRecord 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult signin(@Validated @RequestBody SigninRecord signinRecord) {
        return AjaxResult.success(this.signinRecordService.studentSignin(signinRecord));
    }

    /**
     * 上传签到拍照
     */
    @ApiOperation("上传签到拍照")
    @PostMapping("/uploadPicture")
    public AjaxResult uploadPicture(@RequestParam("file") MultipartFile file) throws Exception {
        if (!file.isEmpty()) {
            String imgUrl = ossService.uploadCommonFile(file);
            AjaxResult ajax = AjaxResult.success();
            ajax.put("url", imgUrl);
            return ajax;
        }
        return AjaxResult.error("上传图片异常，请联系管理员");
    }

//    @PreAuthorize("@ss.hasRole('teacher')")
//    @PostMapping("/export")
//    public void export(HttpServletResponse response, Long taskId) {
//
//        ExcelUtil<SysUser> util = new ExcelUtil<>(SysUser.class);
//        util.exportExcel(response, list, "签到数据");
//    }


}

