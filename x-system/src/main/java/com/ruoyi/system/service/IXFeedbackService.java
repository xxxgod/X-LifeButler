package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.XFeedback;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author ruoyi
 * @date 2024-05-13
 */
public interface IXFeedbackService
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public XFeedback selectFeedbackById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param XFeedback 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<XFeedback> selectFeedbackList(XFeedback XFeedback);

    /**
     * 新增【请填写功能名称】
     * 
     * @param XFeedback 【请填写功能名称】
     * @return 结果
     */
    public int insertFeedback(XFeedback XFeedback);

    /**
     * 修改【请填写功能名称】
     * 
     * @param XFeedback 【请填写功能名称】
     * @return 结果
     */
    public int updateFeedback(XFeedback XFeedback);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteFeedbackByIds(String ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteFeedbackById(Long id);
}
