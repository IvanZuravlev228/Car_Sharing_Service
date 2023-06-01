package com.example.carsharingservice.repository;

import com.example.carsharingservice.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserByEmail(String email);

    List<User> findAllByRole(User.Role role);
}
