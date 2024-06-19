package com.example.auth.controller;


import com.example.auth.model.User;
import com.example.auth.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class userController {


 @Autowired
 private UserService userService;

 @PostMapping("/login")
 public ResponseEntity<User> logInUser(@RequestParam String email,@RequestParam String password) {
   User user = userService.getUserByEmail(email,password);
   System.out.println(user);
   if(user != null ){
    return ResponseEntity.ok(user);
   }
  else{
   return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }
 }

 @PostMapping
 public ResponseEntity<User> createUser(@RequestBody User user) {
  User registeredUser = userService.saveUser(user);
  System.out.println(registeredUser);
  return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
 }


 @DeleteMapping("/delete")
 public ResponseEntity<Void> deleteUser(@RequestParam String email,@RequestParam String password) {
  User user = userService.getUserByEmail(email,password);
  System.out.println("Deleting User: " + user);
  System.out.println("Email: " );
  if (user != null) {
   userService.deleteUser(email,password);
   return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  } else {
   return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
 }


 @PatchMapping("/reset-password")
 public ResponseEntity<User> resetPassword(String fatherName,String email,String password){
  User user = userService.processuserAction(fatherName,email,password);
  if(user != null){
   return ResponseEntity.status(HttpStatus.OK).body(user);
  }
  return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
 }
}
