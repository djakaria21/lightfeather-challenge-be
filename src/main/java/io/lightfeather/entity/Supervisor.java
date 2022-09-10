package io.lightfeather.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Supervisor implements Comparable<Supervisor> {

    Integer id;
    String phone;
    String jurisdiction;
    String identificationNumber;
    String firstName;
    String lastName;

    @Override
    public int compareTo(Supervisor o) {
        int result = this.getJurisdiction().compareTo(o.getJurisdiction());
        if (result == 0) {
            result = this.getLastName().compareTo(o.getLastName());
            if (result == 0) {
                result = this.getFirstName().compareTo(o.getFirstName());
            }
        }
        return result;
    }
}
