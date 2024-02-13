package com.shammocodes.BhaiCycle.service;

import com.shammocodes.BhaiCycle.entity.User;
import com.shammocodes.BhaiCycle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;


    public User saveUser(User user) {
        return userRepository.save(user);
    }
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByVerificationcode(String code) {
        return userRepository.findByVerificationcode(code);
    }
    public User findByMessengerid(String code) {
        return userRepository.findByMessengerid(code);
    }

    public User updateUser(User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());
        existingUser.setActive(user.getActive());
        existingUser.setMessengerid(user.getMessengerid());
        existingUser.setCycle(user.getCycle());
        existingUser.setReq(user.getReq());
        return userRepository.save(existingUser);
    }




    public List<User> getUsers() {
        return userRepository.findAll();
    }
    public boolean hasUser(User signUpData){
        return userRepository.countByUsername(signUpData.getUsername()) > 0;
    }
    public List<User> findTop10ByContributionDesc(){
        return userRepository.findTop10ByOrderByContributionDesc();
    }



}
