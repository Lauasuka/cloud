package io.amoe.cloud.account.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Amoe
 * @date 2020/5/20 13:57
 */
@Data
public class PostMenuDTO implements Serializable {
    @NotBlank(message = "menu code must not blank")
    private String code;

    private String pcode = "0";

    private String pcodes = "[0],";

    @NotBlank(message = "menu name must not blank")
    private String name;

    private String icon;

    @NotBlank(message = "menu url must not blank")
    private String url;

    private Integer sort;

    private Integer levels;

    private String isMenu;

    private String remark;

    private String status;
}
