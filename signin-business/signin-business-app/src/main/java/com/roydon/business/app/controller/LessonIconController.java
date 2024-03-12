package com.roydon.business.app.controller;

import com.roydon.business.app.domain.entity.LessonIcon;
import com.roydon.business.app.domain.vo.PageDataInfo;
import com.roydon.business.app.service.ILessonIconService;
import com.roydon.business.oss.service.OssService;
import com.roydon.common.core.controller.BaseController;
import com.roydon.common.core.domain.AjaxResult;
import com.roydon.common.core.page.TableDataInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * (LessonIcon)表控制层
 *
 * @author roydon
 * @since 2024-01-20 22:49:24
 */
@RestController
@RequestMapping("/app/lessonIcon")
public class LessonIconController extends BaseController {

    @Resource
    private ILessonIconService lessonIconService;

    @Resource
    private OssService ossService;

    @GetMapping("/all")
    public PageDataInfo all() {
        return lessonIconService.getAllIcon();
    }

    /**
     * 分页查询列表
     */
    @PreAuthorize("@ss.hasPermi('app:lessonIcon:list')")
    @GetMapping("/list")
    public TableDataInfo list(LessonIcon lessonIcon) {
        startPage();
        List<LessonIcon> list = lessonIconService.selectLessonIconList(lessonIcon);
        return getDataTable(list);
    }

    /**
     * 图标上传
     */
    @ApiOperation("图标上传")
    @PreAuthorize("@ss.hasPermi('app:lessonIcon:edit')")
    @PostMapping("/uploadIcon")
    public AjaxResult uploadIcon(@RequestParam("file") MultipartFile file) throws Exception {
        if (!file.isEmpty()) {
            String imgUrl = ossService.uploadIconFile(file);
            AjaxResult ajax = AjaxResult.success();
            ajax.put("url", imgUrl);
            return ajax;
        }
        return AjaxResult.error("上传图片异常，请联系管理员");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @GetMapping(value = "/{iconId}")
    public AjaxResult getInfo(@PathVariable("iconId") Long iconId) {
        return success(lessonIconService.selectLessonIconByIconId(iconId));
    }

    /**
     * 新增数据
     *
     * @param lessonIcon 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody LessonIcon lessonIcon) {
        return AjaxResult.success(this.lessonIconService.save(lessonIcon));
    }

    /**
     * 编辑数据
     *
     * @param lessonIcon 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody LessonIcon lessonIcon) {
        return AjaxResult.success(this.lessonIconService.updateById(lessonIcon));
    }

    /**
     * 删除数据
     */
    @DeleteMapping("{iconId}")
    public AjaxResult removeById(@PathVariable("iconId") Long iconId) {
        return AjaxResult.success(this.lessonIconService.removeById(iconId));
    }

}

