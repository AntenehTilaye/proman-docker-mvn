package com.black.promandockermvn;

import java.sql.Date;
import java.util.Optional;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.black.promandockermvn.project.Project;
import com.black.promandockermvn.project.ProjectRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class ProjectRepositoryTests {
    
    @Autowired 
    private ProjectRepository repo;

    @Test
    public void testAddNew(){
        Project project = new Project();
        project.setTitle("Project and publication management");
        project.setAbout("Project and publication management Project and publication management Project and publication management");
        project.setStartDate(Date.valueOf("2023-05-01"));
        project.setEndDate(Date.valueOf("2023-09-10"));

        Project savedProject = repo.save(project);
        Assertions.assertThat(savedProject).isNotNull();
        Assertions.assertThat(savedProject.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAll(){
        Iterable<Project> projects = repo.findAll();
        Assertions.assertThat(projects).hasSizeGreaterThan(0);
    }

    @Test
    public void testGet() {
        Integer projectId = 1;
        Optional<Project> optionalProject = repo.findById(projectId);
        Assertions.assertThat(optionalProject).isPresent();

    }

    @Test
    public void testDelete() {
        Integer projectId = 1;
        repo.deleteById(projectId);
        
        Optional<Project> optionalProject = repo.findById(projectId);
        Assertions.assertThat(optionalProject).isNotPresent();
        
    }

    @Test
    public void testUpdate() {
        Integer projectId = 1;
        Optional<Project> optionalProject = repo.findById(projectId);
        Project project = optionalProject.get();
        project.setTitle("Workflow Management System (Great Project)");
        repo.save(project);

        Project updatedProject = repo.findById(projectId).get();
        Assertions.assertThat(updatedProject.getTitle()).isEqualTo("Workflow Management System (Great Project)");
    }

}