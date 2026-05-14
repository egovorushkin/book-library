package ai.unified.process.demo.book.library.core.ui.layout;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.server.menu.MenuConfiguration;
import com.vaadin.flow.spring.security.AuthenticationContext;
import com.vaadin.flow.theme.lumo.LumoUtility;
import ai.unified.process.demo.book.library.core.ui.LoginView;
import org.springframework.security.core.userdetails.UserDetails;

@AnonymousAllowed
@Layout
public class MainLayout extends AppLayout implements AfterNavigationObserver {

	private final H2 viewTitle = new H2();

	private final transient AuthenticationContext authContext;

	public MainLayout(AuthenticationContext authContext) {
		this.authContext = authContext;
		setPrimarySection(Section.DRAWER);
		addDrawerContent();
		addHeaderContent();
	}

	@Override
	public void afterNavigation(AfterNavigationEvent event) {
		viewTitle.setText(getCurrentPageTitle());
	}

	private void addHeaderContent() {
		var toggle = new DrawerToggle();
		toggle.setAriaLabel("Menu toggle");

		viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

		var userMenu = createUserMenu();
		var navbar = new HorizontalLayout(toggle, viewTitle, userMenu);
		navbar.setWidthFull();
		navbar.setAlignItems(FlexComponent.Alignment.CENTER);
		navbar.expand(viewTitle);
		navbar.addClassNames(LumoUtility.Padding.Horizontal.MEDIUM);

		addToNavbar(true, navbar);
	}

	private HorizontalLayout createUserMenu() {
		var layout = new HorizontalLayout();
		layout.setAlignItems(FlexComponent.Alignment.CENTER);
		layout.addClassNames(LumoUtility.Gap.SMALL);

		authContext.getAuthenticatedUser(UserDetails.class).ifPresentOrElse(user -> {
			var name = new Span(user.getUsername());
			name.addClassNames(LumoUtility.FontWeight.MEDIUM);

			var logout = new Button("Logout", _ -> authContext.logout());
			logout.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);

			layout.add(name, logout);
		}, () -> {
			var loginLink = new RouterLink("Login", LoginView.class);
			loginLink.addClassNames(LumoUtility.FontWeight.MEDIUM);
			layout.add(loginLink);
		});
		return layout;
	}

	private void addDrawerContent() {
		var appName = new Div("Book Library");
		appName.addClassNames(LumoUtility.FontSize.XLARGE, LumoUtility.FontWeight.BLACK, LumoUtility.Margin.MEDIUM);

		var header = new Header(appName);

		var scroller = new Scroller(createNavigation());

		addToDrawer(header, scroller);
	}

	private SideNav createNavigation() {
		return new SideNav();
	}

	private String getCurrentPageTitle() {
		if (getContent() instanceof HasDynamicTitle hasDynamicTitle) {
			return hasDynamicTitle.getPageTitle() == null ? "" : hasDynamicTitle.getPageTitle();
		}
		else if (getContent().getClass().getAnnotation(PageTitle.class) != null) {
			return getContent().getClass().getAnnotation(PageTitle.class).value();
		}
		else {
			return MenuConfiguration.getPageHeader(getContent()).orElse("");
		}
	}

}
