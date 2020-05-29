package io.amoe.cloud.account.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Amoe
 * @date 2020/4/7 17:29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_menu")
public class SysMenu extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "menu_id", type = IdType.ASSIGN_ID)
    private Long menuId;

    /**
     * 菜单编号
     */
    @TableField("code")
    private String code;

    /**
     * 菜单父编号
     */
    @TableField("pcode")
    private String pcode;

    /**
     * 当前菜单的所有父菜单编号
     */
    @TableField("pcodes")
    private String pcodes;

    /**
     * 菜单名称
     */
    @TableField("name")
    private String name;

    /**
     * 菜单图标
     */
    @TableField("icon")
    private String icon;

    /**
     * url地址
     */
    @TableField("url")
    private String url;

    /**
     * 菜单排序号
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 菜单层级
     */
    @TableField("levels")
    private Integer levels;

    /**
     * 是否是菜单(字典)
     */
    @TableField("is_menu")
    private String is_menu;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 菜单状态(字典)
     */
    @TableField("status")
    private String status;
}
