package com.brunodev.taskflow.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.brunodev.taskflow.model.Task;
import com.brunodev.taskflow.repository.TaskRepository;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/tasks")

public class TaskController {

	@Autowired
	private TaskRepository taskRepository;
	
	@GetMapping
	public ResponseEntity<List<Task>> getAll(){
		return ResponseEntity.ok(taskRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Task>getById(@PathVariable Long id){
		return taskRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@GetMapping("/title/{title}")
	public ResponseEntity<List<Task>> getByTitle(@PathVariable String title){
		return ResponseEntity.ok(taskRepository.findAllByTitleContainingIgnoreCase(title));
	}
	
	
	@PostMapping
	public ResponseEntity<Task> post(@Valid @RequestBody Task task){
			return ResponseEntity.status(HttpStatus.CREATED)
				.body(taskRepository.save(task));
	}
	
	@PutMapping
	public ResponseEntity<Task> put(@Valid @RequestBody Task task){
		if(taskRepository.existsById(task.getId())) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(taskRepository.save(task));
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Task> postagem = taskRepository.findById(id);
		
		if(postagem.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		taskRepository.deleteById(id);
	}
	
	
	
	
}
