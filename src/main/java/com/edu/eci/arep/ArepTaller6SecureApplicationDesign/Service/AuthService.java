
package com.edu.eci.arep.ArepTaller6SecureApplicationDesign.Service;

import com.edu.eci.arep.ArepTaller6SecureApplicationDesign.Model.User;
import java.util.Optional;
import com.edu.eci.arep.ArepTaller6SecureApplicationDesign.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Santiago
 */
@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User registerUser(String email, String password){
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new RuntimeException("El usuario ya existe");
        }
        String hashps = passwordEncoder.encode(password);
        User user = new User().builder()
                .email(email)
                .password(hashps)
                .build();
        return userRepository.save(user);
    }

    public Optional<User>loginUser(String email, String password){
        Optional<User>user = userRepository.findByEmail(email);
        if(user.isPresent()&&passwordEncoder.matches(password,user.get().getPassword())) {
            return user;
        }
        return Optional.empty();
    }
}
