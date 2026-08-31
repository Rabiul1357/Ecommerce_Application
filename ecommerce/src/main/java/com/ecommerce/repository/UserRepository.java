package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ecommerce.entity.AppUser;

public interface UserRepository extends JpaRepository<AppUser, Long> {

    AppUser findByUsername(String username);

}