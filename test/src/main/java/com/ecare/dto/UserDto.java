package com.ecare.dto;

import com.ecare.domain.UserRole;
import com.ecare.services.validation.ValidEmail;
import com.ecare.services.validation.ValidPassword;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class UserDto {

    private int userId;
    @ValidEmail
    @NotNull
    @NotEmpty
    @NotBlank
    private String email;

    @ValidPassword
    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 3, max = 10)
    private String password;

    private UserRole userRole;
}
