package com.ruoyi.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jdk.nashorn.internal.parser.JSONParser;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 收入支出对象 x_financial
 * 
 * @author ruoyi
 * @date 2024-08-21
 */
@Data
public class XFinancial extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 收入/支出 */
    @Excel(name = "收入/支出")
    private String type;

    /** 款项 */
    @Excel(name = "款项")
    private String name;

    /** 金额 */
    @Excel(name = "金额")
    private BigDecimal money;

    /** 备注 */
    @Excel(name = "备注")
    private String mark;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Excel(name = "日期")
    private Date recordDate;

}
