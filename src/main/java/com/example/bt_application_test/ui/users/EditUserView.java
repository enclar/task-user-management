//! REFACTOR: Repetitive code, refactor when have more time

package com.example.bt_application_test.ui.users;

import com.example.bt_application_test.backend.controllers.UserController;
import com.example.bt_application_test.backend.data.User;
import com.example.bt_application_test.backend.enums.UserRoleEnum;
import com.example.bt_application_test.backend.service.UserService;
import com.example.bt_application_test.ui.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "edit-user/:userId(\\d+)", layout = MainLayout.class)
@PageTitle("Edit User")
@CssImport("./styles/shared-styles.css")
@PermitAll
public class EditUserView extends VerticalLayout implements BeforeEnterObserver {
    public static final String VIEW_NAME = "Edit User";
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private UserController userController;
    private TextField firstName;
    private TextField lastName;
    private EmailField emailAddress;
    private Select<UserRoleEnum> userRole;
    private Binder<User> userBinder;
    private User userBean;

    @Autowired
    public EditUserView(UserController userController) {
        this.userController = userController;

        setSizeFull();
        setAlignItems(Alignment.CENTER);

        //! form label
        final H1 newUserFormTitle = new H1("Edit User");
        newUserFormTitle.addClassName("main-layout__page-name");

        //! form layout
        firstName = new TextField("First Name");
        lastName = new TextField("Last Name");
        emailAddress = getEmailField();
        userRole = getUserRoleField();
        Span errorMessage = new Span();

        FormLayout userFormLayout = new FormLayout();
        userFormLayout.addClassName("new-user-form__form-layout");
        userFormLayout.add(
            firstName,
            lastName,
            emailAddress,
            userRole,
            errorMessage
        );
        userFormLayout.setColspan(errorMessage, 2);
        userFormLayout.setResponsiveSteps(
            new ResponsiveStep("0", 1),
            new ResponsiveStep("500px", 2)
        );
        userFormLayout.setWidth("50rem");

        HorizontalLayout buttonWrapper = new HorizontalLayout();
        Button saveButton = new Button("Update");
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button backButton = new Button("back");

        buttonWrapper.add(saveButton);

        //! Wrap the form title, form layout, and button in a new VerticalLayout
        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setWidth("50rem");
        contentLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        contentLayout.add(newUserFormTitle, userFormLayout, saveButton);

        //! form binder
        userBinder = new Binder<User>(User.class);
        userBinder.setStatusLabel(errorMessage);

        //! save button click listener
        saveButton.addClickListener(e -> {
            if (userBinder.validate().isOk()) {
                try {
                    userBinder.writeBean(userBean);
                    userController.createUser(userBean);

                    Notification successNotification = Notification.show("User updated successfully!");
                    successNotification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    successNotification.setPosition(Notification.Position.TOP_CENTER);
                    successNotification.setDuration(2000);

                    UI.getCurrent().navigate("users");
                } catch (ValidationException e1) {
                    log.error("Validation error", e1);
                    Notification errorNotification = Notification.show("Error updating user, please try again!");
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
        userRoleSelect.setValue(UserRoleEnum.USER);
        return userRoleSelect;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        List<String> segments = event.getLocation().getSegments();
        if (segments.size() == 2 && segments.get(0).equals("edit-user")) {
            try {
                Integer userId = Integer.parseInt(segments.get(1));
                loadUser(userId);
            } catch (NumberFormatException e) {
                log.error("Invalid userId format", e);
                Notification.show("Invalid user ID", 3000, Notification.Position.TOP_CENTER);
                event.forwardTo("users");
            }
        } else {
            log.error("Unexpected URL format");
            Notification.show("Invalid URL", 3000, Notification.Position.TOP_CENTER);
            event.forwardTo("users");
        }
    }

    private void loadUser(Integer userId) {
        userController.findUserById(userId).ifPresentOrElse(user -> {
            userBean = user;
            setupBinder();
            userBinder.readBean(userBean);
        }, () -> {
            Notification.show("User not found", 3000, Notification.Position.TOP_CENTER);
            UI.getCurrent().navigate("users");
        });
    }

    private void setupBinder() {
        userBinder.forField(firstName).asRequired().bind("firstName");
        userBinder.forField(lastName).bind("lastName");
        userBinder.forField(emailAddress).asRequired().bind("emailAddress");    
        userBinder.forField(userRole).asRequired().bind("userRole");
    }
}
