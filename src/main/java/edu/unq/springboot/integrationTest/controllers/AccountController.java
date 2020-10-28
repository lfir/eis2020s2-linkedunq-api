package edu.unq.springboot.integrationTest.controllers;

import edu.unq.springboot.models.User;
import edu.unq.springboot.integrationTest.controllers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
            return "Registered";
        } else {
            return "Error";
        }
    }

    @RequestMapping(method = { RequestMethod.POST }, value = { "/login" })
    @ResponseBody
    public String logInUser( @RequestBody User user) {
        if (userService.validateUser(user.getUsername(), user.getUsername())) {
            return "OK";
        } else {
            return "Error";
        }
    }
}

