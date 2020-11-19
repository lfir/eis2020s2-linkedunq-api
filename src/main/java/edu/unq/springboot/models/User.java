package edu.unq.springboot.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, unique = true)
    private String username;
    private String password;
    @Column(length = 50)
    private String firstName;
    @Column(length = 50)
    private String lastName;
    @Column(unique = true)
    private String email;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.EAGER) @JsonIgnore
    private List<Job> jobs;
    @Column(length = 50)

    private String link=null;
    @Column(length = 50)
    private String title="Mi Portfolio";
    private Boolean isRecruiter;

    public User() {

    }
    public User(String username, String password, String firstName, String lastName, String email) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.jobs = new ArrayList<Job>();

    }

    public User(String username, String password, String firstName, String lastName, String email, Boolean isRecruiter) {
        this(username, password, firstName, lastName, email);
        this.isRecruiter = isRecruiter;
    }


    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public List<Job> getJobs() { return jobs; }

    public void addJob(Job job) {
        this.jobs.add(job);
    }

    public void generateLink() {
       this.link="http://localhost:3000/repo/:"+this.getUsername();
    }

    public String getLink() {
        return this.link;
    }

    public void modifyTitle (String title) {this.title = title;}

    public String getTitle(){return this.title;}

    public Boolean isRecruiter() {
        return isRecruiter;
    }

    public void setRecruiter(Boolean recruiter) {
        isRecruiter = recruiter;
    }

}
