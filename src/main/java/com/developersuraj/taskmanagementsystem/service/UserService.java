package com.developersuraj.taskmanagementsystem.service;

import com.developersuraj.taskmanagementsystem.data.UserList;
import com.developersuraj.taskmanagementsystem.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired  //injection of implementation
    private UserRepository userRepository;

    public void saveEntry(UserList user){
        userRepository.save(user);
    }

    public List<UserList> getAll(){
        return userRepository.findAll();
    }

    public Optional<UserList> findById(ObjectId id){
        return userRepository.findById(id);
    }

    public void deleteById(ObjectId id){

        userRepository.deleteById(id);
    }

    public UserList findByUserName(String userName){

        return userRepository.findByUserName(userName);

    }

}
