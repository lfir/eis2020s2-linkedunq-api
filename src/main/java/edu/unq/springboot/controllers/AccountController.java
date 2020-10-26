package edu.unq.springboot.controllers;

import edu.unq.springboot.models.User;
import edu.unq.springboot.repository.UserRepository;
import edu.unq.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
@RestController
@Validated
public class AccountController {
    @Autowired
    UserService userService;
    @RequestMapping(method = { RequestMethod.POST }, value = { "/register" })
    @ResponseBody
    public String registerNewUser( @RequestBody User user) {
        if (userService.findByUsername(user.getUsername()) == null) {
            userService.create(user);
            return "OK";
        } else {
            return "Error";
        }

    }
}

