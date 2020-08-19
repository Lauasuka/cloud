package io.amoe.cloud.account.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Amoe
 * @date 2020/4/28 10:50
 */
@Data
public class UserVO implements Serializable {
    private String account;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String roleId;
    private Long deptId;
    private String avatar;
}
