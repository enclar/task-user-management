package com.example.bt_application_test.ui;

import com.example.bt_application_test.ui.tasks.NewTaskView;
import com.example.bt_application_test.ui.tasks.TasksView;
import com.example.bt_application_test.ui.users.NewUserView;
import com.example.bt_application_test.ui.users.UsersView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility;

@CssImport("./styles/shared-styles.css")
public class MainLayout extends AppLayout {

    private Button logoutButton;
    private H1 pageTitle;

    public MainLayout() {
        //! page header elements
        DrawerToggle menuToggle = new DrawerToggle();
        pageTitle = new H1("Users");
        pageTitle.addClassName("main-nav__page-name");

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSpacing(false);
        horizontalLayout.add(menuToggle, pageTitle);
        horizontalLayout.addClassNames("main-nav__title-wrapper", LumoUtility.Gap.XSMALL);

        logoutButton = new Button("Logout");
        logoutButton.addClassName("main-nav__logout-btn");
        logoutButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        logoutButton.addClickListener(e -> {
            VaadinSession.getCurrent().getSession().invalidate();
            UI.getCurrent().getPage().reload();
        });

        FlexLayout navbarLayout = new FlexLayout();
        navbarLayout.setWidthFull();
        navbarLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        navbarLayout.add(horizontalLayout, logoutButton);

        addToNavbar(navbarLayout);

        //! navigation items
        H1 navTitle = new H1("Tasks");
        navTitle.addClassName("side-nav__page-name");
        addToDrawer(navTitle);

        SideNav sideNav = new SideNav();
        
        sideNav.addItem(
            new SideNavItem(UsersView.VIEW_NAME, UsersView.class, VaadinIcon.USERS.create()),
            new SideNavItem(NewUserView.VIEW_NAME, NewUserView.class, VaadinIcon.USER.create()),
            new SideNavItem(TasksView.VIEW_NAME, TasksView.class, VaadinIcon.TASKS.create()),
            new SideNavItem(NewTaskView.VIEW_NAME, NewTaskView.class, VaadinIcon.PENCIL.create())
        );

        Scroller navWrapper = new Scroller(sideNav);
        navWrapper.addClassName("side-nav__tab-wrapper");
        sideNav.setWidthFull();
        addToDrawer(navWrapper);

        setPrimarySection(Section.DRAWER);
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
