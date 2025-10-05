package com.edu.eci.arep.ArepTaller6SecureApplicationDesign.Repository;

import com.edu.eci.arep.ArepTaller6SecureApplicationDesign.Model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Long> {
    Optional<User> findByEmail(String email);
}
