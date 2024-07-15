package com.example.bt_application_test.ui.login;

import org.springframework.aot.generate.AccessControl;

import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("Login")
@PageTitle("Login")
public class LoginView extends FlexLayout {
    private AccessControl accessControl;

    public LoginView() {
    }
}
