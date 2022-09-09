package io.lightfeather.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Supervisor {

    Integer id;
    String phone;
    String jurisdiction;
    String identificationNumber;
    String firstName;
    String lastName;

}
