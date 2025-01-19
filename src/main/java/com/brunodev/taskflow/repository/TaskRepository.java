package com.brunodev.taskflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.brunodev.taskflow.model.Task;

public interface TaskRepository extends JpaRepository <Task, Long>{
	
	public List<Task> findAllByTitleContainingIgnoreCase(@Param("title") String title);
}
