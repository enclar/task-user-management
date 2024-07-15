package com.example.bt_application_test.ui.users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.bt_application_test.backend.controllers.UserController;
import com.example.bt_application_test.backend.data.User;
import com.example.bt_application_test.backend.service.UserService;
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

import java.util.List;

@Route(value = "users", layout = MainLayout.class)
@PageTitle("Users")
public class UsersView extends Div {
    public static final String VIEW_NAME = "Users";
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UsersView(UserController userController) {
        setHeightFull();

        //! setting grid columns
        Grid<User> userGrid = new Grid<>(User.class, false);
        addColumn(userGrid, User::getId, "User Id");
        addColumn(userGrid, User::getFirstName, "First Name");
        addColumn(userGrid, User::getLastName, "Last Name");
        addColumn(userGrid, User::getEmailAddress, "Email Address");
        addColumn(userGrid, User::getPassword, "Password");
        addColumn(userGrid, User::getUserRole, "User Role");
        userGrid.addColumn(
            new ComponentRenderer<HorizontalLayout, User>(
                HorizontalLayout::new,
                (horizontalLayout, user) -> {
                    Button editButton = new Button();
                    editButton.addThemeVariants(
                        ButtonVariant.LUMO_ICON,
                        ButtonVariant.LUMO_TERTIARY
                    );
                    editButton.addClickListener(e -> {
                        UI.getCurrent().navigate("edit-user/" + user.getId());
                    });
                    editButton.setIcon(new Icon(VaadinIcon.EDIT));

                    Button deleteButton = new Button();
                    deleteButton.addThemeVariants(
                        ButtonVariant.LUMO_ICON,
                        ButtonVariant.LUMO_ERROR,
                        ButtonVariant.LUMO_TERTIARY
                    );
                    deleteButton.addClickListener(e -> {
                        userController.deleteUser(user.getId());
                        List<User> users = Lists.newArrayList(userController.findAllUsers());
                        userGrid.setItems(users);
                    });
                    deleteButton.setIcon(new Icon(VaadinIcon.TRASH));

                    horizontalLayout.add(editButton, deleteButton);
                    horizontalLayout.setSpacing(false);
                    horizontalLayout.getThemeList().add("spacing-xs");
                }
            )
        ).setHeader("Manage");

        userGrid.setHeightFull();
        List<User> users = Lists.newArrayList(userController.findAllUsers());
        log.info("UsersView.java", users);
        userGrid.setItems(users);

        add(userGrid);
    }

    private <T> void addColumn(
        Grid<User> userGrid,
        ValueProvider<User, T> colValue,
        String header
    ) {
        userGrid.addColumn(colValue).setHeader(header).setResizable(true);
    }
}
