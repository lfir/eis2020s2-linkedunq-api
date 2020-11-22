package edu.unq.springboot.repository;

import edu.unq.springboot.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUsername(String username);
    public Iterable<User> findByIsRecruiterFalse();
}
