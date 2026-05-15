package ai.unified.process.demo.book.library.core.ui;

import ai.unified.process.demo.book.library.usecase.UseCase;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.RouterLink;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UC001SignInTest extends AbstractBrowserlessTest {

	@Test
	@UseCase(id = "UC-001")
	void login_form_is_opened_on_navigation() {
		LoginView view = navigate(LoginView.class);

		assertThat(view.isOpened()).isTrue();
	}

	@Test
	@UseCase(id = "UC-001", scenario = "A1: Invalid Credentials", businessRules = { "BR-003" })
	void login_form_shows_error_when_error_query_parameter_is_present() {
		UI.getCurrent().navigate(LoginView.class, QueryParameters.of("error", ""));
		LoginView view = (LoginView) getCurrentView();

		assertThat(view.isError()).isTrue();
	}

	@Test
	@UseCase(id = "UC-001", scenario = "A1: Invalid Credentials")
	void login_form_has_no_error_flag_by_default() {
		LoginView view = navigate(LoginView.class);

		assertThat(view.isError()).isFalse();
	}

	@Test
	@UseCase(id = "UC-001", businessRules = { "BR-001" })
	void login_page_has_no_sign_up_link() {
		navigate(LoginView.class);

		assertThat($(RouterLink.class).exists()).isFalse();
	}

	@Test
	@UseCase(id = "UC-001", businessRules = { "BR-003" })
	void error_uses_standard_overlay_flag_not_custom_message() {
		// BR-003: LoginView calls setError(true) — the standard mechanism that shows
		// Vaadin's default i18n "Incorrect username or password" message. It never
		// calls showErrorMessage(title, message), which could inject a user-specific
		// disclosure. We verify the error flag is set and the view is still a LoginView
		// (no redirect to a different error page that could leak info).
		UI.getCurrent().navigate(LoginView.class, QueryParameters.of("error", ""));
		LoginView view = (LoginView) getCurrentView();

		assertThat(view.isError()).isTrue();
		assertThat(view).isInstanceOf(LoginView.class);
	}

}
