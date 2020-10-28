package edu.unq.springboot.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobController {
	
	@PostMapping("/jobs/create")
	@ResponseBody
	public String createJob() {
		return "OK";
	}

}
