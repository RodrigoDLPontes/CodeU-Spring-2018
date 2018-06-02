package codeu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;

/**
 * Servlet implementation class UserProfileServlet
 */
@WebServlet("/UserProfileServlet")
public class UserProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/** Store class that gives access to Users. */
	private UserStore userStore;

	@Override
	public void init() throws ServletException {
		super.init();
		setUserStore(UserStore.getInstance());
	}

	void setUserStore(UserStore userStore) {
		this.userStore = userStore;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		String requestUrl = request.getRequestURI();
		String name = requestUrl.substring("/userprofile/".length());
		request.setAttribute("user", name);
		request.getRequestDispatcher("/WEB-INF/view/userprofile.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String requestUrl = request.getRequestURI();
		String Profile = requestUrl.substring("/userprofile/".length());
		request.setAttribute("user", Profile);

		String User = (String) request.getSession().getAttribute("user");
		if (User != null) {
			String AboutMe = request.getParameter("aboutme");
			// this removes any HTML from the message content
			String cleanedAboutMe = Jsoup.clean(AboutMe, Whitelist.none());
			response.getOutputStream().println("<h1>" + cleanedAboutMe + "</h1>");
			System.out.println(cleanedAboutMe);
			// response.sendRedirect("/userprofile/" + Profile);
			// request.getRequestDispatcher("/userprofile/"+ Profile).forward(request,
			// response);
			return;

		}
	}
}
