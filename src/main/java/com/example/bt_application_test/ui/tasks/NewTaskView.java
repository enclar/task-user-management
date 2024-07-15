package com.example.bt_application_test.ui.tasks;

import com.example.bt_application_test.backend.controllers.TaskController;
import com.example.bt_application_test.backend.controllers.UserController;
import com.example.bt_application_test.backend.data.Task;
import com.example.bt_application_test.backend.data.User;
import com.example.bt_application_test.backend.enums.TaskStateEnum;
import com.example.bt_application_test.backend.service.UserService;
import com.example.bt_application_test.ui.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

@Route(value = "new-task", layout = MainLayout.class)
@PageTitle("New Task")
@CssImport("./styles/shared-styles.css")
@PermitAll
public class NewTaskView extends VerticalLayout {
    public static final String VIEW_NAME = "New Task";
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public NewTaskView(
        UserController userController,
        TaskController taskController
    ) {
        setSizeFull();
        setAlignItems(Alignment.CENTER);

        //! form label
        final H1 newTaskFormTitle = new H1("New Task");
        newTaskFormTitle.addClassName("main-layout__page-name");

        //! form layout
        DateTimePicker createdDateTime = new DateTimePicker("Date & Time Created");
        createdDateTime.setValue(LocalDateTime.now());
        Select<TaskStateEnum> taskState = getTaskStateField();
        Select<User> createdBy = getUserSelect("Created By", userController, 0);
        Select<User> assignedTo = getUserSelect("Assigned To", userController, 1);
        TextField description = new TextField("Description");
        Span errorMessage = new Span();

        FormLayout taskFormLayout = new FormLayout();
        taskFormLayout.addClassName("new-user-form__form-layout");
        taskFormLayout.add(
            createdDateTime,
            taskState,
            createdBy,
            assignedTo,
            description,
            errorMessage
        );
        taskFormLayout.setResponsiveSteps(
            new ResponsiveStep("0", 1),
            new ResponsiveStep("500px", 2)
        );
        taskFormLayout.setColspan(description, 2);
        taskFormLayout.setColspan(errorMessage, 2);
        taskFormLayout.setWidth("50rem");

        Button saveButton = new Button("Save");
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        //! Wrap the form title, form layout, and button in a new VerticalLayout
        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setWidth("50rem");
        contentLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        contentLayout.add(newTaskFormTitle, taskFormLayout, saveButton);

        //! form binder
        Binder<Task> taskBinder = new Binder<Task>(Task.class);

        taskBinder.forField(createdDateTime).asRequired().bind("createdDateTime");
        taskBinder.forField(taskState).asRequired().bind("state");
        taskBinder.forField(createdBy).asRequired().bind("createdBy");
        taskBinder.forField(assignedTo).asRequired().bind("assignedTo");
        taskBinder.forField(description).asRequired().bind("description");

        taskBinder.setStatusLabel(errorMessage);

        //! save button click listener
        saveButton.addClickListener(e -> {
            if (taskBinder.validate().isOk()) {
                try {
                    Task taskBean = new Task();
                    taskBinder.writeBean(taskBean);
                    taskController.createTask(taskBean);

                    Notification successNotification = Notification.show("Task created successfully!");
                    successNotification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    successNotification.setPosition(Notification.Position.TOP_CENTER);
                    successNotification.setDuration(2000);

                    Task emptyTask = new Task();
                    taskBinder.readBean(emptyTask);
                } catch (ValidationException e1) {
                    log.error("Validation error", e1);
                    Notification errorNotification = Notification.show("Error creating task, please try again!");
                    errorNotification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    errorNotification.setPosition(Notification.Position.TOP_CENTER);
                    errorNotification.setDuration(2000);
                }
            } else {
                log.info("Form has validation error");
            }
        });

        add(contentLayout);
    }

    private Select<TaskStateEnum> getTaskStateField() {
        Select<TaskStateEnum> taskStateSelect = new Select<>();
        taskStateSelect.setLabel("State");
        taskStateSelect.setItems(TaskStateEnum.values());
        taskStateSelect.setRenderer(new TextRenderer<>(TaskStateEnum::getDisplayName));
        taskStateSelect.setValue(TaskStateEnum.New);
        return taskStateSelect;
    }

    private Select<User> getUserSelect(
        String label,
        UserController userController,
        Integer index
    ) {
        Select<User> userSelect = new Select<>();
        userSelect.setLabel(label);
        List<User> users = Lists.newArrayList(userController.findAllUsers());
        userSelect.setItems(users);userSelect.setRenderer(new TextRenderer<>(user -> user.getFirstName() + " " + user.getLastName()));
        userSelect.setValue(users.get(index));
        return userSelect;
    }
}
