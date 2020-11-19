package edu.unq.springboot.controllers;

import edu.unq.springboot.models.User;
import edu.unq.springboot.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.validation.constraints.Null;

@RestController
@Validated
public class AccountController {
    @Autowired
    UserService userService;

    @CrossOrigin
    @RequestMapping(method = {RequestMethod.POST}, value = {"/register"})
    @ResponseBody
    public ResponseEntity registerNewUser(@RequestBody User user) {
        if (userService.findByUsername(user.getUsername()) == null) {
            userService.create(user);
            return ResponseEntity.ok("Registered");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error");
        }
    }

    @CrossOrigin
    @RequestMapping(method = {RequestMethod.POST}, value = {"/login"})
    @ResponseBody
    public ResponseEntity logInUser(@RequestBody User user) {
        if (userService.validateUser(user.getUsername(), user.getPassword(), user.isRecruiter())) {
            return ResponseEntity.ok("OK");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error");
        }
    }

    @CrossOrigin
    @RequestMapping(method = {RequestMethod.PUT}, value = {"/link"})
    @ResponseBody
    public ResponseEntity generateLink(@RequestBody User usuario) {
        User user = userService.findByUsername(usuario.getUsername());
        if (user.getLink() == null) {
            user.generateLink();
            userService.updateUser(user);
            System.out.println(usuario.getLink());
        }
        return ResponseEntity.ok(user.getLink());
    }

    @CrossOrigin
    @RequestMapping(method = {RequestMethod.PUT}, value = {"/title"})
    @ResponseBody
    public ResponseEntity modifyTitle(@RequestBody User user) {
        if (userService.findByUsername(user.getUsername()) != null) {
            User userbd = userService.findByUsername(user.getUsername());
            userbd.modifyTitle(user.getTitle());
            userService.updateUser(userbd);
            return ResponseEntity.ok(userbd.getTitle());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username does not exist");
        }
    }

    @CrossOrigin
    @RequestMapping(method =  {RequestMethod.GET}, value = {"/title/{username}"})
    @ResponseBody
    public ResponseEntity getTitle(@PathVariable(value = "username", required = true) String username) {
        if (userService.findByUsername(username) != null) {
            User user = userService.findByUsername(username);
            return ResponseEntity.ok(user.getTitle());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username does not exist");
        }
    }
    
    @CrossOrigin
	@GetMapping("/candidates")
	@ResponseBody
	public List<User> getCandidates() {
		return userService.getNonRecruiters();
	}
}
