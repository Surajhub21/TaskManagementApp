package com.developersuraj.taskmanagementsystem.controller;

import com.developersuraj.taskmanagementsystem.data.UserList;
import com.developersuraj.taskmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> allUser(){
        try {
            List<UserList> all = userService.getAll();
            return new ResponseEntity<>(all , HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping
    public HttpStatus createUser(@RequestBody UserList user){
        try {
            System.out.println("Received User: " + user); // Debug log
            userService.saveEntry(user);
            return HttpStatus.OK;
        }
        catch (Exception e) {
            return HttpStatus.NOT_FOUND;
        }
    }

}
