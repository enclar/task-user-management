package com.example.bt_application_test.backend.data;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.example.bt_application_test.backend.enums.TaskStateEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tasks")
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "created_date_time", nullable = false)
    private LocalDateTime createdDateTime;

    @Column(name = "task_state", nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskStateEnum state;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "assigned_to", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User assignedTo;

    @Column(name = "short_description", nullable = false, columnDefinition = "VARCHAR(255)")
    private String shortDescription;

    @Column(name = "description", columnDefinition = "VARCHAR(510)")
    private String description;

    public Task() {
    }
    
    public Task(
        LocalDateTime createdDateTime,
        TaskStateEnum state,
        User createdBy,
        User assignedTo,
        String shortDescription,
        String description
    ) {
        this.createdDateTime = createdDateTime;
        this.state = state;
        this.createdBy = createdBy;
        this.assignedTo = assignedTo;
        this.shortDescription = shortDescription;
        this.description = description;
    }
}
