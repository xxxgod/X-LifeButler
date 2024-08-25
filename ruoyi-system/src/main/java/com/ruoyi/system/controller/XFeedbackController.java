package com.ruoyi.system.controller;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.XFeedback;
import com.ruoyi.system.service.IXFeedbackService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 【用户反馈】Controller
 * 
 * @author ruoyi
 * @date 2024-05-13
 */
@Controller
@RequestMapping("/system/feedback")
public class XFeedbackController extends BaseController
{
    private String prefix = "system/feedback";

    @Autowired
    private IXFeedbackService feedbackService;

    @RequiresPermissions("system:feedback:view")
    @GetMapping()
    public String feedback()
    {
        return prefix + "/feedback";
    }

    /**
     * 查询【用户反馈】列表
     */
    @RequiresPermissions("system:feedback:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(XFeedback XFeedback)
    {
        startPage();
        List<XFeedback> list = feedbackService.selectFeedbackList(XFeedback);
        return getDataTable(list);
    }

    /**
     * 导出【用户反馈】列表
     */
    @RequiresPermissions("system:feedback:export")
    @Log(title = "【用户反馈】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(XFeedback XFeedback)
    {
        List<XFeedback> list = feedbackService.selectFeedbackList(XFeedback);
        ExcelUtil<XFeedback> util = new ExcelUtil<XFeedback>(XFeedback.class);
        return util.exportExcel(list, "【用户反馈】数据");
    }

    /**
     * 新增【用户反馈】
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存【用户反馈】
     */
    @RequiresPermissions("system:feedback:add")
    @Log(title = "【用户反馈】", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(XFeedback XFeedback)
    {
        return toAjax(feedbackService.insertFeedback(XFeedback));
    }

    /**
     * 修改【用户反馈】
     */
    @RequiresPermissions("system:feedback:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        XFeedback XFeedback = feedbackService.selectFeedbackById(id);
        mmap.put("feedback", XFeedback);
        return prefix + "/edit";
    }

    /**
     * 修改保存【用户反馈】
     */
    @RequiresPermissions("system:feedback:edit")
    @Log(title = "【用户反馈】", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(XFeedback XFeedback)
    {
        return toAjax(feedbackService.updateFeedback(XFeedback));
    }

    /**
     * 删除【用户反馈】
     */
    @RequiresPermissions("system:feedback:remove")
    @Log(title = "【用户反馈】", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(feedbackService.deleteFeedbackByIds(ids));
    }
}
