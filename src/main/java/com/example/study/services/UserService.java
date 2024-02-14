package com.example.study.services;

import com.example.study.models.User;
import com.example.study.repositories.TaskRepository;
import com.example.study.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User findById(Integer id){
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(()-> new RuntimeException(
                "Usuario não encontrado " + id
        ));
    }

    @Transactional
    public User create(User user){
        user.setId(null);
        user = this.userRepository.save(user);
        return user;
    }

    @Transactional
    public User update(User user){
        User newUser = findById(user.getId());
        newUser.setPassword(user.getPassword());
        return this.userRepository.save(newUser);
    }

    public void delete(Integer id){
        findById(id);
        try {
            this.userRepository.deleteById(id);
        }catch (Exception e){
            throw new RuntimeException("Não é possível deletar, há entidades relacionadas");
        }
    }
}
