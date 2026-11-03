package com.example.demo.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
    Optional<Project> findById(UUID id);
    @Query("SELECT p FROM Project p LEFT JOIN FETCH p.tasks")
    List<Project> findAll();
    
    @Query("SELECT p FROM Project p JOIN FETCH p.tasks")
    List<Project> findAllWithTasks();
}
