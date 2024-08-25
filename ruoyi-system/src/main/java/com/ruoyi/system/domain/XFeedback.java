package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 feedback
 * 
 * @author ruoyi
 * @date 2024-05-13
 */
public class XFeedback extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */

    private Long id;

    /** 留言 */
    @Excel(name = "留言")
    private String message;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 邮箱 */
    @Excel(name = "邮箱")
    private String email;

    /** 手机 */
    @Excel(name = "手机")
    private String phone;

    /** 反馈类型（1:关于我 2:留言） */
    @Excel(name = "反馈类型", readConverterExp = "1=:关于我,2=:留言")
    private String type;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setMessage(String message) 
    {
        this.message = message;
    }

    public String getMessage() 
    {
        return message;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setEmail(String email) 
    {
        this.email = email;
    }

    public String getEmail() 
    {
        return email;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("message", getMessage())
            .append("name", getName())
            .append("email", getEmail())
            .append("phone", getPhone())
            .append("type", getType())
            .append("createTime", getCreateTime())
            .toString();
    }
}
