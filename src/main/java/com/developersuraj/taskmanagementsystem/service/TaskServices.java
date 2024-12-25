package com.developersuraj.taskmanagementsystem.service;

import com.developersuraj.taskmanagementsystem.data.TaskList;
import com.developersuraj.taskmanagementsystem.data.UserList;
import com.developersuraj.taskmanagementsystem.repository.TasksRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class TaskServices {

    @Autowired
    private TasksRepository tasksRepository;
    @Autowired
    private UserService userService;

    //Save the data
    @Transactional
    public void saveData(TaskList taskList,String userName){
        try {
            UserList user = userService.findByUserName(userName);
            taskList.setDate(LocalDateTime.now());
            TaskList saved = tasksRepository.save(taskList);
            user.getTaskLists().add(saved);
            userService.saveEntry(user);
        } catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("An error occurred while saving the entry, ", e);
        }
    }
    //Get all data
    public List<TaskList> getAllData(){
        return tasksRepository.findAll();
    }
    //Delete the data
    public void deleteDataById(String title ,String userName){

        UserList user = userService.findByUserName(userName);
        user.getTaskLists().removeIf( x ->

                x.getTitle().equals(title)

        );

        userService.saveEntry(user);
        tasksRepository.deleteByTitle(title);
    }

}
