package com.example.bt_application_test.backend.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.bt_application_test.backend.data.Task;

public interface TaskRepository extends CrudRepository<Task, Integer> {
    
}
