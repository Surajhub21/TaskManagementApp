package com.developersuraj.taskmanagementsystem.controller;

import com.developersuraj.taskmanagementsystem.data.UserList;
import com.developersuraj.taskmanagementsystem.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userName}")
    public boolean getTheUser(@PathVariable String userName){

        UserList user = userService.findByUserName(userName);

        if(user != null){
            return true;
        }

        return false;

    }

    @DeleteMapping("/{userName}/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String userName , @PathVariable ObjectId id){
        UserList user = userService.findByUserName(userName);

        if(user != null){
            userService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody UserList userList , @PathVariable String userName){

        UserList userInDB = userService.findByUserName(userName);

        if(userList != null){
            userInDB.setUserName(userList.getUserName());
            userInDB.setPassword(userList.getPassword());
            userService.saveEntry(userInDB);

            return new ResponseEntity<>(HttpStatus.RESET_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
