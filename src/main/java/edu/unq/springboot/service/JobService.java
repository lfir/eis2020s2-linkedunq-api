package edu.unq.springboot.service;

import edu.unq.springboot.models.Job;
import edu.unq.springboot.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    private final JobRepository jobRepository;

    @Autowired
    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public Job update(Job trabajo) {
        return jobRepository.save(trabajo);
    }

    List<Job> findByUsername(String username) {
        return jobRepository.findByUsername(username);
    }

}
