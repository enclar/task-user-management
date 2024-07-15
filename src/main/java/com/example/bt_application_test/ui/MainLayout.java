package com.example.bt_application_test.ui;

import com.example.bt_application_test.ui.tasks.NewTaskView;
import com.example.bt_application_test.ui.tasks.TasksView;
import com.example.bt_application_test.ui.users.NewUserView;
import com.example.bt_application_test.ui.users.UsersView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;

@CssImport("./styles/shared-styles.css")
public class MainLayout extends AppLayout implements RouterLayout {

    private Button logoutButton;
    private H1 pageTitle;

    public MainLayout() {
        //! page header elements
        final DrawerToggle menuToggle = new DrawerToggle();
        pageTitle = new H1("Users");
        pageTitle.addClassName("main-nav__page-name");
        addToNavbar(menuToggle, pageTitle);

        //! navigation items
        addToDrawer(createMenuLink(
            UsersView.class,
            UsersView.VIEW_NAME,
            VaadinIcon.USERS.create()
        ));
        addToDrawer(createMenuLink(
            NewUserView.class,
            NewUserView.VIEW_NAME,
            VaadinIcon.USER.create()
        ));
        addToDrawer(createMenuLink(
            TasksView.class,
            TasksView.VIEW_NAME,
            VaadinIcon.TASKS.create()
        ));
        addToDrawer(createMenuLink(
            NewTaskView.class,
            NewTaskView.VIEW_NAME,
            VaadinIcon.PENCIL.create()
        ));

        setPrimarySection(Section.DRAWER);
    }

    private RouterLink createMenuLink(
        Class<? extends Component> viewClass,
        String label,
        Icon icon
    ) {
        final RouterLink routerLink = new RouterLink(viewClass);
        routerLink.add(icon);
        routerLink.add(new Span(label));
        icon.setSize("1rem");
        routerLink.setClassName("side-nav__router-link");
        return routerLink;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        getPageTitle();
    }

    private void getPageTitle() {
        Component content = getContent();
        if (content != null) {
            PageTitle pageTitleAnnotation = content.getClass().getAnnotation(PageTitle.class);
            if (pageTitleAnnotation != null) {
                pageTitle.setText(pageTitleAnnotation.value());
            } else {
                pageTitle.setText("Task Management");
            }
        }
    }
}    
