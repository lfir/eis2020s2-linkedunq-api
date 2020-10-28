package edu.unq.springboot.controllers;

import javax.servlet.http.HttpServletRequest;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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

}
