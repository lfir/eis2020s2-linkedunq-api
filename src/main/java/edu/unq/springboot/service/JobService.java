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
        return this.jobRepository.save(trabajo);
    }

	public void update(String username, Long id, Job editedJob) {
		for (Job j : this.findByUsername(username)) {
			if (j.getId().equals(id)) {
				j.setDescripcion(editedJob.getDescripcion());
				j.setTitulo(editedJob.getTitulo());
				j.setFechaInicioTrabajo(editedJob.getFechaInicioTrabajo());
				j.setFechaFinTrabajo(editedJob.getFechaFinTrabajo());
				j.setEnlace(editedJob.getEnlace());
				this.jobRepository.save(j);
			}
		}
	}

	public List<Job> findByUsername(String username) {
        return this.jobRepository.findByUsername(username);
    }

    public void deleteJobById(Long id){
        this.jobRepository.deleteJobById(id);
    }

    public Job findJobById(Long id){
        return jobRepository.findJobById(id);
    }
}
