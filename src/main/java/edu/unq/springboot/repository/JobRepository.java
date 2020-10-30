package edu.unq.springboot.repository;

import edu.unq.springboot.IntegrationTest.models.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    @Query("SELECT j FROM Job j WHERE j.owner.username = ?1")
    List<Job> findByUsername(String username);

}
