package com.birthright.web.dto;

import com.birthright.validation.PasswordMatches;
import com.birthright.validation.ValidEmail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
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

    @ValidEmail
    @NotNull
    @NotEmpty(message = "Email is empty or Email exists")
    private String email;

    @NotNull
    @NotEmpty(message = "Password must be from 6 to 20 character")
    @Size(min = 6, max = 20)
    private String password;

    @NotNull
    @NotEmpty(message = "Password must be from 6 to 20 character")
    @Size(min = 6, max = 20)
    private String matchingPassword;
}
