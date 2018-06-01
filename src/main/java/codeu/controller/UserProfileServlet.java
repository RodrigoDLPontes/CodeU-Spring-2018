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
import codeu.model.store.basic.UserStore;

/**
 * Servlet implementation class UserProfileServlet
 */
@WebServlet("/UserProfileServlet")
public class UserProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserProfileServlet() {
		super();
	}

	/** Store class that gives access to Users. */
	private UserStore userStore;

	/** Set up State for handling login-related request. */
	@Override
	public void init() throws ServletException {
		super.init();
		setUserStore(UserStore.getInstance());
	}

	/**
	 * Sets the UserStore used by this servlet. This function provides a common
	 * setup method for use by the test framework or the servlet's init() function.
	 */
	void setUserStore(UserStore userStore) {
		this.userStore = userStore;

	}

	/**
	 * allows us to generate different URLs for the profile page depending on the
	 * user
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String requestUrl = request.getRequestURI();
		String Profile = requestUrl.substring("/userprofile/".length());
		request.setAttribute("user", Profile);
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
			System.out.println(cleanedAboutMe);
			response.sendRedirect("/userprofile/" + Profile);
			
			return;
		

		}

	}

}
