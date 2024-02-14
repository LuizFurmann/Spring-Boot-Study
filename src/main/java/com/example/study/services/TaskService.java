package com.example.study.services;

import com.example.study.models.Task;
import com.example.study.models.User;
import com.example.study.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public Task findById(Integer id){
        Optional<Task> task = this.taskRepository.findById(id);
        return task.orElseThrow(()-> new RuntimeException(
                "Tarefa não encontrada"
        ));
    }

    @Transactional
    public Task create(Task task){
        User user = this.userService.findById(task.getUser().getId());
        task.setId(null);
        task.setUser(user);
        task = this.taskRepository.save(task);
        return task;
    }

    @Transactional
    public Task update(Task task){
        Task newTask = findById(task.getId());
        newTask.setDesciption(task.getDesciption());
        return this.taskRepository.save(newTask);
    }

    public void delete(Integer id){
        findById(id);
        try {
            this.taskRepository.deleteById(id);
        }catch (Exception e){
            throw new RuntimeException("Não é possível deletar pois ha entidade relacionada");
        }
    }
}
