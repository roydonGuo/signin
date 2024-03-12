package com.roydon.business.app.controller;

import com.roydon.business.app.domain.dto.SigninTaskCrateDTO;
import com.roydon.business.app.domain.dto.SigninTaskPageDTO;
import com.roydon.business.app.domain.vo.PageDataInfo;
import com.roydon.business.app.service.ISigninTaskService;
import com.roydon.common.core.domain.AjaxResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 签到任务表(SigninTask)表控制层
 *
 * @author roydon
 * @since 2024-02-16 16:43:48
 */
@RestController
@RequestMapping("/app/signinTask")
public class SigninTaskController {

    @Resource
    private ISigninTaskService signinTaskService;

    /**
     * 新增数据
     *
     * @param signinTaskCrateDTO 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SigninTaskCrateDTO signinTaskCrateDTO) {
        return AjaxResult.success(this.signinTaskService.addTask(signinTaskCrateDTO));
    }

    /**
     * 分页签到任务
     *
     * @param signinTaskPageDTO
     * @return
     */
    @PostMapping("/page")
    public PageDataInfo page(@RequestBody SigninTaskPageDTO signinTaskPageDTO) {
        return signinTaskService.taskPage(signinTaskPageDTO);
    }

    /**
     * 获取待签到任务
     *
     * @return
     */
    @GetMapping("/waitSignin")
    public AjaxResult waitSignin() {
        return AjaxResult.success(signinTaskService.getWaitSignin());
    }

    /**
     * 获取签到记录
     *
     * @param taskId
     * @return
     */
    @PreAuthorize("@ss.hasRole('teacher')")
    @GetMapping("/record/{taskId}")
    public AjaxResult recordList(@PathVariable("taskId") Long taskId) {
        return AjaxResult.success(signinTaskService.getSigninRecordList(taskId));
    }

}

