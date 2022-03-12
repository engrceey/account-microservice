package com.reloadly.accountservice.repository;

import com.reloadly.accountservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
