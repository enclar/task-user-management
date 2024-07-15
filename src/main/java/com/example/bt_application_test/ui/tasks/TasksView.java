package com.example.bt_application_test.ui.tasks;

import com.example.bt_application_test.backend.controllers.TaskController;
import com.example.bt_application_test.backend.data.Task;
import com.example.bt_application_test.backend.data.User;
import com.example.bt_application_test.ui.MainLayout;
import com.google.common.collect.Lists;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Route(value = "tasks", layout = MainLayout.class)
@PageTitle("Tasks")
@PermitAll
public class TasksView extends Div {
    public static final String VIEW_NAME = "Tasks";

    public TasksView(TaskController taskController) {
        setHeightFull();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss");

        //! setting grid columns
        Grid<Task> taskGrid = new Grid<>(Task.class, false);
        addColumn(taskGrid, Task::getId, "Task Id");
        addColumn(taskGrid, task -> task.getCreatedDateTime().format(dateTimeFormatter), "Date Time Created");
        addColumn(
            taskGrid,
            task -> {
                User createdBy = task.getCreatedBy();
                if (createdBy != null) {
                    return createdBy.getFirstName() + " " + createdBy.getLastName();
                }
                return "";
            },
            "Created By"
        );
        addColumn(
            taskGrid,
            task -> {
                User assignedTo = task.getAssignedTo();
                if (assignedTo != null) {
                    return assignedTo.getFirstName() + " " + assignedTo.getLastName();
                }
                return "";
            },
            "Assigned To"
        );
        addColumn(taskGrid, Task::getShortDescription, "Short Description");
        addColumn(taskGrid, Task::getDescription, "Description");
        addColumn(taskGrid, task -> task.getState().getDisplayName(), "State");
        taskGrid.addColumn(
            new ComponentRenderer<HorizontalLayout, Task>(
                HorizontalLayout::new,
                (horizontalLayout, task) -> {
                    Button editButton = new Button();
                    editButton.addThemeVariants(
                        ButtonVariant.LUMO_ICON,
                        ButtonVariant.LUMO_TERTIARY
                    );
                    editButton.addClickListener(e -> {
                        UI.getCurrent().navigate("edit-task/" + task.getId());
                    });
                    editButton.setIcon(new Icon(VaadinIcon.EDIT));

                    Button deleteButton = new Button();
                    deleteButton.addThemeVariants(
                        ButtonVariant.LUMO_ICON,
                        ButtonVariant.LUMO_ERROR,
                        ButtonVariant.LUMO_TERTIARY
                    );
                    deleteButton.addClickListener(e -> {
                        taskController.deleteTask(task.getId());
                        List<Task> tasks = Lists.newArrayList(taskController.findAllTasks());
                        taskGrid.setItems(tasks);
                    });
                    deleteButton.setIcon(new Icon(VaadinIcon.TRASH));

                    horizontalLayout.add(editButton, deleteButton);
                    horizontalLayout.setSpacing(false);
                    horizontalLayout.getThemeList().add("spacing-xs");
                }
            )
        ).setHeader("Manage");

        taskGrid.setHeightFull();
        List<Task> tasks = Lists.newArrayList(taskController.findAllTasks());
        taskGrid.setItems(tasks);

        add(taskGrid);
    }

    private <T> void addColumn(
        Grid<Task> taskGrid,
        ValueProvider<Task, T> colValue,
        String header
    ) {
        taskGrid.addColumn(colValue).setHeader(header).setResizable(true);
    }
}
