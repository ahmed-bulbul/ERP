package com.bulbul.erp.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupRequest {

    @NotBlank(message = "username not be blank")
    private String username;

    @NotBlank
    @Email
    private String email;

    private Set<String> role;

    @Size(message = "password size must be 4 to 14", min = 4,max = 14)
    @NotBlank
    private String password;

}
