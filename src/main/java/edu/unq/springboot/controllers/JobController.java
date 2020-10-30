package edu.unq.springboot.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.unq.springboot.IntegrationTest.models.CreateJobRequestBody;
import edu.unq.springboot.IntegrationTest.models.Job;
import edu.unq.springboot.IntegrationTest.models.User;
import edu.unq.springboot.service.JobService;
import edu.unq.springboot.service.UserService;

@RestController
public class JobController {
	@Autowired
    private UserService userService;
	@Autowired
    private JobService jobService;
	
	@PostMapping("/jobs/create")
	@ResponseBody
	public String createJob(@RequestBody CreateJobRequestBody bd) {
		User usuario = userService.findByUsername(bd.getUsername());
		Job job = new Job(
			usuario, bd.getTitulo(), bd.getDescripcion(), 
			LocalDate.parse(bd.getFechaInicioTrabajo()), LocalDate.parse(bd.getFechaFinTrabajo())
		);

		userService.addJob(job, usuario);

		return "OK";
	}
	
	@GetMapping("/jobs")
	@ResponseBody
	public List<Job> getUserJobs(@RequestParam(value = "username", required = true) String username) {
		return jobService.findByUsername(username);
	}
}
