package com.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.entity.AppUser;
import com.ecommerce.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public AppUser save(AppUser user) {

		user.setRole("USER");

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		return repository.save(user);
	}

	public AppUser findByUsername(String username) {
		return repository.findByUsername(username);
	}
}