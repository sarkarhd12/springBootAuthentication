package com.example.auth.service;

import com.example.auth.model.User;
import com.example.auth.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUserByEmail(String email,String password){
        User user = userRepository.findByEmail(email);
        System.out.println(user);
        System.out.println(email);
        if (user == null) {
            System.out.println("User does not exist");
            return null;
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            System.out.println("Wrong password");
            user.setLoginAttempt(user.getLoginAttempt()+1);
            userRepository.save(user);
            if(user.getLoginAttempt()>3){
                System.out.println("reset password");
            }
            return null;
        }
 user.setLoginAttempt(0);
        userRepository.save(user);
        return user;
    }


    public User saveUser(@NotNull User user){
        String email=user.getEmail();
        User existingUser = userRepository.findByEmail(email);
        if(existingUser != null) {
            throw new RuntimeException("user already exist");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);

    }

    public void deleteUser(String email,String password){
        User user = userRepository.findByEmail(email);
        System.out.println(email);
        System.out.println(user);
        if(user != null && passwordEncoder.matches(password, user.getPassword())){
            userRepository.deleteByEmail(email);
            System.out.println("user dleted");
        }
        else{
            System.out.println("user does not exist");
        }
    }

    public User processuserAction(String fatherName,String email,String password){
       User user = userRepository.findByEmail(email);
       if(user != null && user.getFatherName().equals(fatherName)){
           user.setPassword(passwordEncoder.encode(password));
           return userRepository.save(user);
       }
       if(user != null){
           System.out.println("incorrect answer of question");
       }
       return null;
    }

}
