package ai.unified.process.demo.book.library.core.ui.layout;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.menu.MenuConfiguration;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.util.Locale;

@Layout
public class MainLayout extends AppLayout implements AfterNavigationObserver {

	private final H2 viewTitle = new H2();

	public MainLayout() {
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

		addToNavbar(true, toggle, viewTitle);
	}

	private void addDrawerContent() {
		var appName = new Div("Book Library");
		appName.addClassNames(LumoUtility.FontSize.XLARGE, LumoUtility.FontWeight.BLACK, LumoUtility.Margin.MEDIUM);

		var header = new Header(appName);

		var scroller = new Scroller(createNavigation());

		addToDrawer(header, scroller, createFooter());
	}

	private SideNav createNavigation() {
		var nav = new SideNav();
		return nav;
	}

	private Footer createFooter() {
		var footer = new Footer();
		var verticalLayout = new VerticalLayout();
		footer.add(verticalLayout);

		var locale = UI.getCurrent().getSession().getLocale();

		var languageSwitchEn = new Button("EN");
		languageSwitchEn.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
		languageSwitchEn.setEnabled(!Locale.ENGLISH.getLanguage().equals(locale.getLanguage()));
		languageSwitchEn.addClickListener(_ -> switchLanguage(Locale.ENGLISH.getLanguage()));

		var languageSwitchDe = new Button("DE");
		languageSwitchDe.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
		languageSwitchDe.setEnabled(!Locale.GERMAN.getLanguage().equals(locale.getLanguage()));
		languageSwitchDe.addClickListener(_ -> switchLanguage(Locale.GERMAN.getLanguage()));

		var languageLayout = new HorizontalLayout(languageSwitchEn, languageSwitchDe);
		languageLayout.addClassNames(LumoUtility.Margin.SMALL, LumoUtility.Margin.Top.XLARGE);
		verticalLayout.add(languageLayout);

		return footer;
	}

	private void switchLanguage(String language) {
		UI.getCurrent().getSession().setLocale(Locale.of(language, UI.getCurrent().getLocale().getCountry()));
		UI.getCurrent().getPage().reload();
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
