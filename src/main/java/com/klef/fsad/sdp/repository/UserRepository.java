package com.klef.fsad.sdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.klef.fsad.sdp.entity.User;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Integer> {
	public User findByUsername(String username);
	public User findByUsernameAndPassword(String username, String password);
}
