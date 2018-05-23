package codeu.controller;

import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import java.util.ArrayList;

/** Servlet class responsible for the admin page. */
public class AdminServlet extends HttpServlet {

  /** Store class that gives access to Users. */
  private UserStore userStore;

  /**
   * Set up state for handling admin-related requests. This method is only called when
   * running in a server, not when running in a test.
   */
  @Override
  public void init() throws ServletException {
    super.init();
    setUserStore(UserStore.getInstance());
  }

  /**
   * Sets the UserStore used by this servlet. This function provides a common setup method for use
   * by the test framework or the servlet's init() function.
   */
  void setUserStore(UserStore userStore) {
    this.userStore = userStore;
  }

  /**
   * This function fires when a user navigates to the admin page.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    String username = (String) request.getSession().getAttribute("user");

    if (username == null) {
      request.setAttribute("unregistered_user", "You must login and be an admin to view this page.");
    } else if (!isAdmin(username)) {
      request.setAttribute("non_admin", "You are not authorized to view this page");
    } else {
      request.setAttribute("admin", "Hi " + username + "! Welcome to the admin page!");
    }

    request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
    return;
  }

  public boolean isAdmin(String username) {
    ArrayList<String> admins = new ArrayList<String>();
    admins.add("amejia");
    admins.add("shershey");
    admins.add("Rodrigo");
    admins.add("quinykb");
    admins.add("Israel");
    
    return admins.contains(username);
  }
}
