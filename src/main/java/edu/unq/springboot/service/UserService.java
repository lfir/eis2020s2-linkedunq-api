package edu.unq.springboot.service;

import edu.unq.springboot.models.Job;
import edu.unq.springboot.models.User;
import edu.unq.springboot.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    public Boolean validateUser(String username, String password) {
        User usuario = userRepository.findByUsername(username);
        if (usuario != null && usuario.getPassword().equals(password)) {
            return usuario.isRecruiter();
        } else {
            return null;
        }
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    public void updateUser(User usuario) {
        userRepository.save(usuario);
    }
    
    public List<User> getNonRecruiters() {
    	return StreamSupport.stream(this.userRepository.findByIsRecruiterFalse().spliterator(), false)
    		       .collect(Collectors.toList());
    }
}
