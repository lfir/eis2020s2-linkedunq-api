package edu.unq.springboot.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import edu.unq.springboot.models.CreateJobRequestBody;
import edu.unq.springboot.models.Job;
import edu.unq.springboot.models.User;
import edu.unq.springboot.service.JobService;
import edu.unq.springboot.service.UserService;

@RestController
public class JobController {
	@Autowired
    private UserService userService;
	@Autowired
    private JobService jobService;

	@CrossOrigin
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
	
	@CrossOrigin
	@PostMapping("/jobs/edit")
	@ResponseBody
	public String editJob(@RequestParam(value = "username", required = true) String username, 
			@RequestParam(value = "id", required = true) Long id,
			@RequestBody Job editedJob) {
		jobService.update(username, id, editedJob); 
		return "OK";
	}

	@CrossOrigin
	@GetMapping("/jobs")
	@ResponseBody
	public List<Job> getUserJobs(@RequestParam(value = "username", required = true) String username) {
		return jobService.findByUsername(username);
	}
}
