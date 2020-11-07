package edu.unq.springboot.controllers;

import edu.unq.springboot.models.CreateJobRequestBody;
import edu.unq.springboot.models.Job;
import edu.unq.springboot.models.User;
import edu.unq.springboot.service.JobService;
import edu.unq.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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
	@GetMapping("/jobs")
	@ResponseBody
	public List<Job> getUserJobs(@RequestParam(value = "username", required = true) String username) {
		return jobService.findByUsername(username);
	}

	@CrossOrigin
	@RequestMapping (method = {RequestMethod.DELETE}, value = "/job/{id}")
	@ResponseBody
	public ResponseEntity deleteUserJob(@PathVariable(value = "id", required = true) Long id){
		Job job = jobService.findJobById(id);
		if (job == null){
			return ResponseEntity.notFound().build();
		}
		jobService.deleteJobById(job.getId());
		return ResponseEntity.ok().build();
	}
}
