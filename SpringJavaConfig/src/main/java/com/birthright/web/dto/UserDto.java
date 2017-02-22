package com.birthright.web.dto;

import com.birthright.validation.PasswordMatches;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by birth on 11.02.2017.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatches
public class UserDto {

    @NotNull
    @NotEmpty(message = "Username is empty or Username exists")
    private String username;

    @Pattern(regexp = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    @NotNull
    @NotEmpty(message = "Email is empty or Email exists")
    private String email;

    @NotNull
    @NotEmpty(message = "Password must be from 6 to 20 character")
    @Size(min = 6, max = 24)
    private String password;

    @NotNull
    @NotEmpty(message = "Password must be from 6 to 20 character")
    @Size(min = 6, max = 24)
    private String matchingPassword;

}
