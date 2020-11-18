package edu.unq.springboot.service;

import edu.unq.springboot.models.Job;
import edu.unq.springboot.models.User;
import edu.unq.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User usuario) {
        return userRepository.save(usuario);
    }

    public User addJob(Job trabajo, User usuario) {
        usuario.addJob(trabajo);
        return userRepository.save(usuario);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Boolean validateUser(String username, String password, Boolean isRecruiter) {
        User usuario = userRepository.findByUsername(username);
        boolean response=false;
        if(isRecruiter==null)
            response=usuario != null && password.equals(usuario.getPassword()) ;
        else
            response=usuario != null && password.equals(usuario.getPassword())&& isRecruiter.equals(usuario.isRecruiter());

        return response;
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    public void updateUser(User usuario) {
        userRepository.save(usuario);
    }
}
