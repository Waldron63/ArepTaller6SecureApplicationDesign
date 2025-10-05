package com.edu.eci.arep.ArepTaller6SecureApplicationDesign.Model.DTO;

import lombok.*;


/**
 *
 * @author Santiago
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {

    private String email;
    private String password;
}
