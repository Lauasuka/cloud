package io.amoe.cloud.account.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Amoe
 * @date 2020/6/2 16:40
 */
@Data
public class PostUserLoginDTO implements Serializable {
    @NotBlank(message = "{blank.account}")
    private String account;

    @NotBlank(message = "{blank.password}")
    private String password;

    @NotBlank(message = "{blank.verification.code}")
    private String code;
}
