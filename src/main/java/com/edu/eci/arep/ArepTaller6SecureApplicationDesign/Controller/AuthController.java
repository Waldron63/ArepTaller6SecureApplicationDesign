
package com.edu.eci.arep.ArepTaller6SecureApplicationDesign.Controller;

import com.edu.eci.arep.ArepTaller6SecureApplicationDesign.Model.DTO.UserDto;
import com.edu.eci.arep.ArepTaller6SecureApplicationDesign.Model.User;
import com.edu.eci.arep.ArepTaller6SecureApplicationDesign.Service.AuthService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 *
 * @author Santiago
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto user) {
        authService.registerUser(user.getEmail(), user.getPassword());
        return ResponseEntity.ok("Registro Exitoso");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto user) {
        Optional<User> loggedUser = authService.loginUser(user.getEmail(), user.getPassword());
        if (loggedUser.isPresent()) {
            return ResponseEntity.ok("Login Exitoso");
        } else {
            return ResponseEntity.status(401).body("Credenciales NO válidas");
        }
    }
}
