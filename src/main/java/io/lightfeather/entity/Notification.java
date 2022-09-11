package io.lightfeather.entity;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class Notification {

    @NotBlank
    @Pattern(regexp="^[A-Za-z]*$",message = "Invalid Input")
    private String firstName;

    @NotBlank
    @Pattern(regexp="^[A-Za-z]*$",message = "Invalid Input")
    private String lastName;

    @Email(message = "Invalid Email")
    private String email;

    @Pattern(regexp = "^(\\d{3}[- .]?){2}\\d{4}$", message = "Invalid Phone Number")
    private String phoneNumber;

    @NotBlank
    private String supervisor;
}
