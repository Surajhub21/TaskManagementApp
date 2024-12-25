package com.developersuraj.taskmanagementsystem.repository;

import com.developersuraj.taskmanagementsystem.data.UserList;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserList , ObjectId> {

    UserList findByUserName(String username);

}
