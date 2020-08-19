package io.amoe.cloud.account.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class PutUserDTO implements Serializable {
    @NotNull(message = "{null.id}")
    private Long userId;
    private String name;
    private String phone;
}
