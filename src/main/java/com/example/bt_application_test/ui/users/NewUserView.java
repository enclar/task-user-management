package com.example.bt_application_test.ui.users;

import com.example.bt_application_test.backend.controllers.UserController;
import com.example.bt_application_test.backend.data.User;
import com.example.bt_application_test.backend.enums.TaskStateEnum;
import com.example.bt_application_test.backend.enums.UserRoleEnum;
import com.example.bt_application_test.backend.service.UserService;
import com.example.bt_application_test.ui.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "new-user", layout = MainLayout.class)
@PageTitle("New User")
@CssImport("./styles/shared-styles.css")
@PermitAll
public class NewUserView extends VerticalLayout {
    public static final String VIEW_NAME = "New User";
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public NewUserView(UserController userController) {
        setSizeFull();
        setAlignItems(Alignment.CENTER);

        //! form label
        final H1 newUserFormTitle = new H1("New User");
        newUserFormTitle.addClassName("main-layout__page-name");

        //! form layout
        TextField firstName = new TextField("First Name");
        TextField lastName = new TextField("Last Name");
        EmailField emailAddress = getEmailField();
        PasswordField password = new PasswordField("Password");
        Select<UserRoleEnum> userRole = getUserRoleField();
        Span errorMessage = new Span();


        FormLayout userFormLayout = new FormLayout();
        userFormLayout.addClassName("new-user-form__form-layout");
        userFormLayout.add(
            firstName,
            lastName,
            emailAddress,
            password,
            userRole,
            errorMessage
        );
        userFormLayout.setColspan(errorMessage, 2);
        userFormLayout.setResponsiveSteps(
            new ResponsiveStep("0", 1),
            new ResponsiveStep("500px", 2)
        );
        userFormLayout.setWidth("50rem");

        Button saveButton = new Button("Save");
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        //! Wrap the form title, form layout, and button in a new VerticalLayout
        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setWidth("50rem");
        contentLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        contentLayout.add(newUserFormTitle, userFormLayout, saveButton);

        //! form binder
        Binder<User> userBinder = new Binder<User>(User.class);

        userBinder.forField(firstName).asRequired().bind("firstName");
        userBinder.forField(lastName).bind("lastName");
        userBinder.forField(emailAddress).asRequired().bind("emailAddress");
        userBinder.forField(password).asRequired().bind("password");
        userBinder.forField(userRole).asRequired().bind("userRole");

        userBinder.setStatusLabel(errorMessage);

        //! save button click listener
        saveButton.addClickListener(e -> {
            if (userBinder.validate().isOk()) {
                try {
                    User userBean = new User();
                    userBinder.writeBean(userBean);
                    userController.createUser(userBean);

                    Notification successNotification = Notification.show("User created successfully!");
                    successNotification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    successNotification.setPosition(Notification.Position.TOP_CENTER);
                    successNotification.setDuration(2000);

                    User emptyUser = new User();
                    userBinder.readBean(emptyUser);
                } catch (ValidationException e1) {
                    log.error("Validation error", e1);
                    Notification errorNotification = Notification.show("Error creating user, please try again!");
                    errorNotification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    errorNotification.setPosition(Notification.Position.TOP_CENTER);
                    errorNotification.setDuration(2000);
                }
            } else {
                log.info("Form has validation errors");
            }
        });

        add(contentLayout);
    }

    private EmailField getEmailField() {
        EmailField emailField = new EmailField("Email address");
        emailField.getElement().setAttribute("name", "emailAddress");
        emailField.setErrorMessage("Enter a valid email address");
        emailField.setClearButtonVisible(true);
        return emailField;
    }

    private Select<UserRoleEnum> getUserRoleField() {
        Select<UserRoleEnum> userRoleSelect = new Select<>();
        userRoleSelect.setLabel("User Role");
        userRoleSelect.setItems(UserRoleEnum.values());
        userRoleSelect.setRenderer(new TextRenderer<>(UserRoleEnum::getDisplayName));
        userRoleSelect.setValue(UserRoleEnum.USER);
        return userRoleSelect;
    }
}
