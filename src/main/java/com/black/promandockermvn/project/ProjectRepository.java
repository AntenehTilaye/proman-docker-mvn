package com.black.promandockermvn.project;

import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Integer>{
    public Long countById(Integer id);
}