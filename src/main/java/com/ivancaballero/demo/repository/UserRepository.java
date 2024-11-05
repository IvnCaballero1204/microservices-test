package com.ivancaballero.demo.repository;

import com.ivancaballero.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
