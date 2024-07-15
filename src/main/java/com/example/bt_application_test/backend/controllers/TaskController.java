package com.example.bt_application_test.backend.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.bt_application_test.backend.data.Task;
import com.example.bt_application_test.backend.service.TaskService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;


@RestController
public class TaskController {
    private static final Logger log = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/api/tasks")
    public @ResponseBody Iterable<Task> findAllTasks() {
        return taskService.findAll();
    }

    public Optional<Task> findTaskById(Integer id) {
        return taskService.findById(id);
    }

    public void deleteTask(Integer id) {
        taskService.delete(id);
    }

    public void createTask(@RequestBody Task task) {
        taskService.createOrUpdate(task);
    }
}
