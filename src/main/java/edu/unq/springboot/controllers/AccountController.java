package edu.unq.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@Validated
public class AccountController {
    @Autowired
    @RequestMapping(method = { RequestMethod.POST }, value = { "/register" })
        public String registerNewUser(HttpServletResponse response) {
            return "OK";
        }
}

