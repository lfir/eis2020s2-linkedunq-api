package edu.unq.springboot.controllers;

import edu.unq.springboot.IntegrationTest.models.User;
import edu.unq.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
public class AccountController {
    @Autowired
    UserService userService;
    @CrossOrigin

    @RequestMapping(method = { RequestMethod.POST }, value = { "/register" })
    @ResponseBody

    public ResponseEntity registerNewUser( @RequestBody User user) {
        if (userService.findByUsername(user.getUsername()) == null) {
            userService.create(user);
            return ResponseEntity.ok("Registered");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error");
        }
    }
    @CrossOrigin

    @RequestMapping(method = { RequestMethod.POST }, value = { "/login" })
    @ResponseBody
    public ResponseEntity logInUser( @RequestBody User user) {
        if (userService.validateUser(user.getUsername(), user.getPassword())) {
            return ResponseEntity.ok("OK");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error");
        }
    }
}

