package com.klef.fsad.sdp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.klef.fsad.sdp.entity.User;
import com.klef.fsad.sdp.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    // 🔥 SAFE SIGNUP METHOD
    @Override
    public String userRegistration(User user) {
        try {

            if (user == null) {
                return "User object is null";
            }

            if (user.getUsername() == null || user.getUsername().isBlank()) {
                return "Username is required";
            }

            if (user.getPassword() == null || user.getPassword().isBlank()) {
                return "Password is required";
            }

            if (user.getEmail() == null || user.getEmail().isBlank()) {
                return "Email is required";
            }

            // optional duplicate check
            if (userRepository.findByUsername(user.getUsername()) != null) {
                return "Username already exists";
            }

            if (passwordEncoder == null) {
                return "PasswordEncoder not initialized";
            }

            // encode password
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            userRepository.save(user);

            return "User registered successfully";

        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }

    // 🔥 LOGIN METHOD
    @Override
    public User verifyUserLogin(String username, String pwd) {
        try {
            User user = userRepository.findByUsername(username);

            if (user != null && passwordEncoder.matches(pwd, user.getPassword())) {
                return user;
            }
            return null;

        } catch (Exception e) {
            return null;
        }
    }

    // 🔥 UPDATE PROFILE
    @Override
    public String updateuserProfile(User user) {
        Optional<User> optional = userRepository.findById(user.getId());

        if (optional.isPresent()) {
            User u = optional.get();

            if (user.getName() != null)
                u.setName(user.getName());

            if (user.getContact() != null)
                u.setContact(user.getContact());

            userRepository.save(u);

            return "User Profile Updated Successfully";
        } else {
            return "User Id not found to update";
        }
    }

    // 🔥 GET USER
    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // 🔥 SPRING SECURITY METHOD (still needed)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("USER"))
        );
    }
}
