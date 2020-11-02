package com.mandoob.services;

import com.mandoob.models.User;
import com.mandoob.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Transactional
    public void createUser(User user) {
        user.setActivated(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public boolean mailIsExist(User user) {
        return userRepository.existsByEmail(user.getEmail());
    }

    public boolean mailIsExist(String email) {
        return userRepository.existsByEmail(email);
    }

    public User getUser(Long id) {
        User user = userRepository.findById(id).get();
        if (user == null)
            throw new IllegalArgumentException("Invalid User Id:" + id);
        return user;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.getOne(id);
        if (user == null)
            throw new IllegalArgumentException("Invalid User Id:" + id);
        userRepository.delete(user);
    }

    @Transactional
    public void editUser(User user) {
        userRepository.save(user);
    }


}
