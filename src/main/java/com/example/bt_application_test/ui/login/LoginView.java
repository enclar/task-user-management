package com.example.bt_application_test.ui.login;

import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

// @Route("login")
// @PageTitle("Login")
// @AnonymousAllowed
// public class LoginView extends LoginOverlay {
//     public LoginView() {
//         setAction("login");
//         setOpened(true);
//         setTitle("Task Management");
//         setDescription("Login to access the application");
//     }
// }

@Route("login")
@PageTitle("Login")
@AnonymousAllowed
public class LoginView extends VerticalLayout {

    public LoginView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        LoginForm loginForm = new LoginForm();
        loginForm.setAction("login");

        add(loginForm);
    }
}
