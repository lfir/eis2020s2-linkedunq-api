package edu.unq.springboot.cucumber.userSteps;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;

import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import edu.unq.springboot.models.Job;
import edu.unq.springboot.models.User;
import edu.unq.springboot.repository.JobRepository;
import edu.unq.springboot.service.JobService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@RunWith(SpringRunner.class)
public class EditJobStepDef {
	private Job job;
	private Job editedJob;
	private User user = new User("Ricardo", "password", "firstname", "lastname", "ricardo@gmail.com");
	private JobService jobService;
	private JobRepository mock_jobRepository = mock(JobRepository.class);
	
	@Given("Job with title {string}")
	public void setJobTitle(String title) {
		this.job = new Job(user, title, "Desc", LocalDate.parse("2010-10-20"),
				LocalDate.parse("2015-08-10"), "http://ml.ca", "http://img.us");
	}
	
	@When("I set {string} as the new title of the job")
	public void updateJobTitle(String title) {
		this.editedJob = new Job(user, title, "Desc", LocalDate.parse("2010-10-20"),
				LocalDate.parse("2015-08-10"), "http://ml.ca", "http://img.us");
		given(this.mock_jobRepository.findJobById((long) 1)).willReturn(this.job);
		this.jobService = new JobService(this.mock_jobRepository);

		this.jobService.update(this.user.getUsername(), (long) 1, this.editedJob);
	}
	
	@Then("The job has value {string} as title")
	public void jobHasTitle(String title) {
		verify(this.mock_jobRepository, times(1))
			.save(argThat(j -> j.getTitulo().equals(this.editedJob.getTitulo())));
	}
}
