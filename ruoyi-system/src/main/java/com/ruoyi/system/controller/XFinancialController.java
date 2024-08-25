package com.ruoyi.system.controller;

import java.util.Date;
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
import com.ruoyi.system.domain.XFinancial;
import com.ruoyi.system.service.IXFinancialService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 收入支出Controller
 * 
 * @author ruoyi
 * @date 2024-08-21
 */
@Controller
@RequestMapping("/system/financial")
public class XFinancialController extends BaseController
{
    private String prefix = "system/financial";

    @Autowired
    private IXFinancialService xFinancialService;

    @RequiresPermissions("system:financial:view")
    @GetMapping()
    public String financial()
    {
        return prefix + "/financial";
    }

    /**
     * 查询收入支出列表
     */
    @RequiresPermissions("system:financial:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(XFinancial xFinancial)
    {
        startPage();

        List<XFinancial> list = xFinancialService.selectXFinancialList(xFinancial);
        return getDataTable(list);
    }

    /**
     * 导出收入支出列表
     */
    @RequiresPermissions("system:financial:export")
    @Log(title = "收入支出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(XFinancial xFinancial)
    {

        List<XFinancial> list = xFinancialService.selectXFinancialList(xFinancial);
        ExcelUtil<XFinancial> util = new ExcelUtil<XFinancial>(XFinancial.class);
        return util.exportExcel(list, "收入支出数据");
    }

    /**
     * 新增收入支出
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存收入支出
     */
    @RequiresPermissions("system:financial:add")
    @Log(title = "收入支出", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(XFinancial xFinancial)
    {
        return toAjax(xFinancialService.insertXFinancial(xFinancial));
    }

    /**
     * 修改收入支出
     */
    @RequiresPermissions("system:financial:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        XFinancial xFinancial = xFinancialService.selectXFinancialById(id);
        mmap.put("xFinancial", xFinancial);
        return prefix + "/edit";
    }

    /**
     * 修改保存收入支出
     */
    @RequiresPermissions("system:financial:edit")
    @Log(title = "收入支出", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(XFinancial xFinancial)
    {
        return toAjax(xFinancialService.updateXFinancial(xFinancial));
    }

    /**
     * 删除收入支出
     */
    @RequiresPermissions("system:financial:remove")
    @Log(title = "收入支出", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(xFinancialService.deleteXFinancialByIds(ids));
    }
}
