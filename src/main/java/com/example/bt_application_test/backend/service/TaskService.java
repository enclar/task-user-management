package com.example.bt_application_test.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bt_application_test.backend.data.Task;
import com.example.bt_application_test.backend.repository.TaskRepository;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TaskService {
    private static final Logger log = LoggerFactory.getLogger(TaskService.class);
    private TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Iterable<Task> findAll() {
        Iterable<Task> taskList = taskRepository.findAll();
        return taskList;
    }

    public Optional<Task> findById(Integer id) {
        return taskRepository.findById(id);
    }

    public void delete(Integer id) {
        taskRepository.deleteById(id);
    }

    public void createOrUpdate(Task task) {
        taskRepository.save(task);
    }
}
