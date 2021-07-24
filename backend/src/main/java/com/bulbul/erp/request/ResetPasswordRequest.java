package com.bulbul.erp.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest {

    @NotBlank(message = "old password not be blank")
    private String oldPassword;

    @NotBlank
    @Size(message = "new password size must be 5 to 14", min = 4,max = 14)
    private String newPassword;
}
