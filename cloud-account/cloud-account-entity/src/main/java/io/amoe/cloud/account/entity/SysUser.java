package io.amoe.cloud.account.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Amoe
 * @date 2020/4/7 16:45
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
public class SysUser extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "user_id", type = IdType.ASSIGN_ID)
    private Long userId;

    /**
     * 头像
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 账号
     */
    @TableField("account")
    private String account;

    /**
     * 密码
     */
    @TableField("password")
    @JsonIgnore
    private String password;

    /**
     * md5密码盐
     */
    @TableField("salt")
    private String salt;

    /**
     * 名字
     */
    @TableField("name")
    private String name;

    /**
     * 电子邮件
     */
    @TableField("email")
    private String email;

    /**
     * 电话
     */
    @TableField("phone")
    private String phone;

    /**
     * 角色id(多个逗号隔开)
     */
    @TableField("role_id")
    private String roleId;

    /**
     * 部门id(多个逗号隔开)
     */
    @TableField("dept_id")
    private String deptId;

    /**
     * 状态(字典)
     */
    @TableField("status")
    private String status;
}
