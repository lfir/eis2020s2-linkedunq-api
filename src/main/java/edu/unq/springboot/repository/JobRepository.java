package edu.unq.springboot.repository;

import edu.unq.springboot.models.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    @Query("SELECT j FROM Job j WHERE j.owner.username = ?1")
    List<Job> findByUsername(String username);

    @Query("SELECT j FROM Job j WHERE j.id = :id")
    Job findJobById(@Param("id")Long id);

    @Query("SELECT j FROM Job j WHERE j.owner.username =:username  ORDER BY j.prioridad")
    List<Job> findByUsernameOrderedByPrioridad(@Param("username") String username);

    @Query("SELECT j FROM Job j WHERE j.owner.username = ?1 ORDER BY j.fechaFinTrabajo")
    List<Job> findByUsernameOrderedByfechaFinTrabajo(String username);

    @Transactional
    @Modifying
    @Query("DELETE FROM Job j WHERE j.id =:id")
    void deleteJobById(@Param("id") Long id);
}
