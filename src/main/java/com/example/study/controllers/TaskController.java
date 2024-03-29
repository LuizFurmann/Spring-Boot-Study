package com.example.study.controllers;

import com.example.study.models.Task;
import com.example.study.services.TaskService;
import com.example.study.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/task")
@Validated
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<Task> findById(@PathVariable Integer id){
        Task task = this.taskService.findById(id);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<List<Task>> findAllByTaskUserId(@PathVariable Integer user_id){
        this.userService.findById(user_id);
        List<Task> tasks = this.taskService.findAllByUserId(user_id);
        return ResponseEntity.ok().body(tasks);
    }

    @PostMapping
    @Validated
    public ResponseEntity<Void> create(@Valid @RequestBody Task task){
        this.taskService.create(task);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(task.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @Validated
    public ResponseEntity<Void> update(@Valid @RequestBody Task task, @PathVariable Integer id){
        task.setId(id);
        this.taskService.update(task);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        this.taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
