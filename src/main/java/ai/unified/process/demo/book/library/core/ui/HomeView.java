package ai.unified.process.demo.book.library.core.ui;

import ai.unified.process.demo.book.library.core.security.CurrentUser;
import ai.unified.process.demo.book.library.core.security.Role;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@AnonymousAllowed
@Route("")
@PageTitle("Book Library")
public class HomeView extends VerticalLayout implements BeforeEnterObserver {

	private final CurrentUser currentUser;

	public HomeView(CurrentUser currentUser) {
		this.currentUser = currentUser;
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		currentUser.get().ifPresent(user -> {
			if (user.role() == Role.LIBRARIAN) {
				event.forwardTo("loans");
			}
			else {
				event.forwardTo("catalog");
			}
		});
	}

}
