package edu.unq.springboot.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

public class UserController {
    @RequestMapping(method = { RequestMethod.POST }, value = { "/register" })
        public String registerNewUser(HttpServletResponse response) {
            return "OK";
        }
}

