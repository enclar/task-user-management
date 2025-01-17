package com.example.bt_application_test.ui.tasks;

import com.example.bt_application_test.backend.controllers.TaskController;
import com.example.bt_application_test.backend.controllers.UserController;
import com.example.bt_application_test.backend.data.Task;
import com.example.bt_application_test.backend.data.User;
import com.example.bt_application_test.backend.enums.TaskStateEnum;
import com.example.bt_application_test.backend.service.UserService;
import com.example.bt_application_test.ui.MainLayout;
import com.vaadin.flow.component.UI;
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
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

@Route(value = "edit-task/:taskId(\\d+)", layout = MainLayout.class)
@PageTitle("Edit Task")
@CssImport("./styles/shared-styles.css")
@PermitAll
public class EditTaskView extends VerticalLayout implements BeforeEnterObserver {
    public static final String VIEW_NAME = "Edit Task";
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private UserController userController;
    private TaskController taskController;
    private DateTimePicker createdDateTime;
    private Select<TaskStateEnum> taskState;
    private Select<User> createdBy;
    private Select<User> assignedTo;
    private TextField shortDescription;
    private TextField description;
    private Span errorMessage;
    private Binder<Task> taskBinder;
    private Task taskBean;

    public EditTaskView(
        UserController userController,
        TaskController taskController
    ) {
        this.userController = userController;
        this.taskController = taskController;

        setSizeFull();
        setAlignItems(Alignment.CENTER);

        //! form label
        final H1 newTaskFormTitle = new H1("Edit Task");
        newTaskFormTitle.addClassName("main-layout__page-name");

        //! form layout
        createdDateTime = new DateTimePicker("Date & Time Created");
        createdDateTime.setValue(LocalDateTime.now());
        taskState = getTaskStateField();
        createdBy = getUserSelect("Created By", userController);
        assignedTo = getUserSelect("Assigned To", userController);
        shortDescription = new TextField("Short Description");
        description = new TextField("Description");
        errorMessage = new Span();

        FormLayout taskFormLayout = new FormLayout();
        taskFormLayout.addClassName("new-user-form__form-layout");
        taskFormLayout.add(
            createdDateTime,
            taskState,
            createdBy,
            assignedTo,
            shortDescription,
            description,
            errorMessage
        );
        taskFormLayout.setResponsiveSteps(
            new ResponsiveStep("0", 1),
            new ResponsiveStep("500px", 2)
        );
        taskFormLayout.setColspan(shortDescription, 2);
        taskFormLayout.setColspan(description, 2);
        taskFormLayout.setColspan(errorMessage, 2);
        taskFormLayout.setWidth("50rem");

        HorizontalLayout buttonWrapper = new HorizontalLayout();
        Button saveButton = new Button("Update");
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button backButton = new Button("Back");
        backButton.addClickListener(e -> {
            UI.getCurrent().navigate("tasks");
        });
        buttonWrapper.add(saveButton, backButton);

        //! Wrap the form title, form layout, and button in a new VerticalLayout
        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setWidth("50rem");
        contentLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        contentLayout.add(newTaskFormTitle, taskFormLayout, buttonWrapper);

        //! form binder
        taskBinder = new Binder<Task>(Task.class);

        taskBinder.setStatusLabel(errorMessage);

        //! save button click listener
        saveButton.addClickListener(e -> {
            if (taskBinder.validate().isOk()) {
                try {
                    taskBinder.writeBean(taskBean);
                    taskController.createTask(taskBean);

                    Notification successNotification = Notification.show("Task updated successfully!");
                    successNotification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    successNotification.setPosition(Notification.Position.TOP_CENTER);
                    successNotification.setDuration(2000);

                    UI.getCurrent().navigate("tasks");
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
        taskStateSelect.setValue(TaskStateEnum.NEW);
        return taskStateSelect;
    }

    private Select<User> getUserSelect(
        String label,
        UserController userController
    ) {
        Select<User> userSelect = new Select<>();
        userSelect.setLabel(label);
        List<User> users = Lists.newArrayList(userController.findAllUsers());
        userSelect.setItems(users);userSelect.setRenderer(new TextRenderer<>(user -> user.getFirstName() + " " + user.getLastName()));
        return userSelect;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        List<String> segments = event.getLocation().getSegments();
        if (segments.size() == 2 && segments.get(0).equals("edit-task")) {
            try {
                Integer taskId = Integer.parseInt(segments.get(1));
                loadTask(taskId);
            } catch (NumberFormatException e) {
                log.error("Invalid taskId format", e);
                Notification.show("Invalid task ID", 3000, Notification.Position.TOP_CENTER);
                event.forwardTo("tasks");
            }
        } else {
            log.error("Unexpected URL format");
            Notification.show("Invalid URL", 3000, Notification.Position.TOP_CENTER);
            event.forwardTo("tasks");
        }
    }

    private void loadTask(Integer taskId) {
        taskController.findTaskById(taskId).ifPresentOrElse(task -> {
            taskBean = task;
            setupBinder();
            taskBinder.readBean(taskBean);
        }, () -> {
            Notification.show("Task not found", 3000, Notification.Position.TOP_CENTER);
            UI.getCurrent().navigate("tasks");
        });
    }

    private void setupBinder() {
        taskBinder.forField(createdDateTime).asRequired().bind("createdDateTime").setReadOnly(true);
        taskBinder.forField(taskState).asRequired().bind("state");
        taskBinder.forField(createdBy).asRequired().bind(
            task -> findCreatedByUserInList(task.getCreatedBy()),
            (task, user) -> task.setCreatedBy(user)
        );
        taskBinder.forField(assignedTo).asRequired().bind(
            task -> findAssignedToUserInList(task.getAssignedTo()),
            (task, user) -> task.setAssignedTo(user)
        );
        taskBinder.forField(shortDescription).asRequired().bind("shortDescription");
        taskBinder.forField(description).bind("description");
    }

    private User findCreatedByUserInList(User user) {
        if (user == null) return null;
        return createdBy.getListDataView().getItems()
            .filter(u -> u.getId().equals(user.getId()))
            .findFirst()
            .orElse(null);
    }

    private User findAssignedToUserInList(User user) {
        if (user == null) return null;
        return assignedTo.getListDataView().getItems()
            .filter(u -> u.getId().equals(user.getId()))
            .findFirst()
            .orElse(null);
    }
}
