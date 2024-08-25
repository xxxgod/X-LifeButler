package com.ruoyi.system.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.XFinancialMapper;
import com.ruoyi.system.domain.XFinancial;
import com.ruoyi.system.service.IXFinancialService;
import com.ruoyi.common.core.text.Convert;

import static com.ruoyi.common.utils.ShiroUtils.getLoginName;

/**
 * 收入支出Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-08-21
 */
@Service
public class XFinancialServiceImpl implements IXFinancialService 
{
    @Autowired
    private XFinancialMapper xFinancialMapper;

    /**
     * 查询收入支出
     * 
     * @param id 收入支出主键
     * @return 收入支出
     */
    @Override
    public XFinancial selectXFinancialById(Long id)
    {
        return xFinancialMapper.selectXFinancialById(id);
    }

    /**
     * 查询收入支出列表
     * 
     * @param xFinancial 收入支出
     * @return 收入支出
     */
    @Override
    public List<XFinancial> selectXFinancialList(XFinancial xFinancial)
    {
        String loginName = getLoginName();
        return xFinancialMapper.selectXFinancialList(xFinancial);
    }

    /**
     * 新增收入支出
     * 
     * @param xFinancial 收入支出
     * @return 结果
     */
    @Override
    public int insertXFinancial(XFinancial xFinancial)
    {
        xFinancial.setCreateTime(DateUtils.getNowDate());
        return xFinancialMapper.insertXFinancial(xFinancial);
    }

    /**
     * 修改收入支出
     * 
     * @param xFinancial 收入支出
     * @return 结果
     */
    @Override
    public int updateXFinancial(XFinancial xFinancial)
    {
        return xFinancialMapper.updateXFinancial(xFinancial);
    }

    /**
     * 批量删除收入支出
     * 
     * @param ids 需要删除的收入支出主键
     * @return 结果
     */
    @Override
    public int deleteXFinancialByIds(String ids)
    {
        return xFinancialMapper.deleteXFinancialByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除收入支出信息
     * 
     * @param id 收入支出主键
     * @return 结果
     */
    @Override
    public int deleteXFinancialById(Long id)
    {
        return xFinancialMapper.deleteXFinancialById(id);
    }
}
