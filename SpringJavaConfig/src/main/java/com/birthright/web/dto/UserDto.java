package com.birthright.web.dto;

import com.birthright.validation.PasswordMatches;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

/**
 * Created by birth on 11.02.2017.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatches
public class UserDto {

    @Pattern(regexp = "^[a-zA-Z0-9_-]{3,15}$", message = "The length from 3 to 15, with no special characters")
    private String username;

    @Pattern(regexp = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    private String email;

    @Pattern(regexp = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$", message = "UpperCase, LowerCase, Number/SpecialChar and min 8 Chars")
    private String password;

    @Pattern(regexp = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$", message = "UpperCase, LowerCase, Number/SpecialChar and min 8 Chars")
    private String matchingPassword;

}
