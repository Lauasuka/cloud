package io.amoe.cloud.account.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author Amoe
 * @date 2020/5/26 11:18
 */
@Data
public class PostUserDTO implements Serializable {

    private String avatar = "1";

    /**
     * 账号
     */
    @NotBlank(message = "{blank.account}")
    private String account;

    /**
     * 密码
     */
    @NotBlank(message = "{blank.password}")
    private String password;

    /**
     * 名字
     */
    @NotBlank(message = "{blank.name}")
    private String name;

    /**
     * 生日
     */
    @Past(message = "{past.date.birthday}")
    private LocalDate birthday;

    /**
     * 性别(字典)
     */
    private String sex;

    /**
     * 电子邮件
     */
    @Email(message = "{format.email}")
    private String email;

    /**
     * 电话
     */
    @NotBlank(message = "{blank.phone}")
    private String phone;
}
