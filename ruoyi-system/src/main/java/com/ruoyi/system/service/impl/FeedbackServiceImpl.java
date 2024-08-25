package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.XFeedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.XFeedbackMapper;
import com.ruoyi.system.service.IXFeedbackService;
import com.ruoyi.common.core.text.Convert;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-05-13
 */
@Service
public class FeedbackServiceImpl implements IXFeedbackService
{
    @Autowired
    private XFeedbackMapper XFeedbackMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public XFeedback selectFeedbackById(Long id)
    {
        return XFeedbackMapper.selectFeedbackById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param XFeedback 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<XFeedback> selectFeedbackList(XFeedback XFeedback)
    {
        return XFeedbackMapper.selectFeedbackList(XFeedback);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param XFeedback 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertFeedback(XFeedback XFeedback)
    {
        XFeedback.setCreateTime(DateUtils.getNowDate());
        return XFeedbackMapper.insertFeedback(XFeedback);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param XFeedback 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateFeedback(XFeedback XFeedback)
    {
        return XFeedbackMapper.updateFeedback(XFeedback);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteFeedbackByIds(String ids)
    {
        return XFeedbackMapper.deleteFeedbackByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteFeedbackById(Long id)
    {
        return XFeedbackMapper.deleteFeedbackById(id);
    }
}
