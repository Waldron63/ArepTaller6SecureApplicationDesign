package com.edu.eci.arep.ArepTaller6SecureApplicationDesign.Model;

import jakarta.persistence.*;
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
@Entity
@Builder
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", nullable = false, updatable = false)
    private Long id;
    @Column(name="email")
    private String email;
    @Column(name="password")
    private String password;

    @Override
    public String toString() {
        return String.format(
                "User[id=%d, email='%s', password='%s']",
                id, email, password);
    }
}
