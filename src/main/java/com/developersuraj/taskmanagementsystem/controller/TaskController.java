package com.developersuraj.taskmanagementsystem.controller;

import com.developersuraj.taskmanagementsystem.data.TaskList;
import com.developersuraj.taskmanagementsystem.data.UserList;
import com.developersuraj.taskmanagementsystem.service.TaskServices;
import com.developersuraj.taskmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskServices taskServices;

    @Autowired
    private UserService userService;

    // Fetch all tasks for a given user
    @GetMapping("/{userName}")
    public ResponseEntity<?> getAllData(@PathVariable String userName) {

        UserList user = userService.findByUserName(userName);
        List<TaskList> allTasks = user.getTaskLists();

        if (allTasks != null && !allTasks.isEmpty()) {
            return new ResponseEntity<>(allTasks, HttpStatus.OK);
        }
        return new ResponseEntity<>("No tasks found", HttpStatus.NOT_FOUND);
    }

    // Create a new task for a user
    @PostMapping("/{userName}")
    public ResponseEntity<?> createData(@RequestBody TaskList taskList, @PathVariable String userName) {
        try {
            taskServices.saveData(taskList, userName);
            return new ResponseEntity<>(taskList, HttpStatus.CREATED); // Return created task
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating task: " + e.getMessage(), HttpStatus.BAD_REQUEST); // Return error response
        }
    }

    // Delete a task by task ID for a specific user
    @DeleteMapping("/{userName}/{title}")
    public ResponseEntity<?> deleteData(@PathVariable String userName, @PathVariable String title) {
        try {
//            ObjectId taskId = new ObjectId(myId); // Convert myId to ObjectId
            taskServices.deleteDataById(title, userName);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting task: " + e.getMessage(), HttpStatus.BAD_REQUEST); // Return error response
        }
    }
}
