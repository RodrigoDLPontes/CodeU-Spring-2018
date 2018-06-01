package codeu.controller;

import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/** Servlet class responsible for the admin page. */
public class AdminServlet extends HttpServlet {

  /** Store class that gives access to Users. */
  private UserStore userStore;
  private ConversationStore conversationStore;
  private MessageStore messageStore;
  boolean isRegistered, isAdmin;

  /**
   * Set up state for handling admin-related requests. This method is only called when
   * running in a server, not when running in a test.
   */
  @Override
  public void init() throws ServletException {
    super.init();
    setUserStore(UserStore.getInstance());
    setConversationStore(ConversationStore.getInstance());
    setMessageStore(MessageStore.getInstance());
  }

  /**
   * Sets the UserStore used by this servlet. This function provides a common setup method for use
   * by the test framework or the servlet's init() function.
   */
  void setUserStore(UserStore userStore) {
    this.userStore = userStore;
  }

  /**
   * Sets the ConversationStore used by this servlet. This function provides a common setup method for use
   * by the test framework or the servlet's init() function.
   */
  void setConversationStore(ConversationStore conversationStore) {
    this.conversationStore = conversationStore;
  }

  /**
   * Sets the MessageStore used by this servlet. This function provides a common setup method for use
   * by the test framework or the servlet's init() function.
   */
  void setMessageStore(MessageStore messageStore) {
    this.messageStore = messageStore;
  }

  /**
   * Serves the admin page for the logged in user. Can access the page directly from /admin url
   * or can click through from the homepage (link shown only to admins).
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    String username = (String) request.getSession().getAttribute("user");

    if (username == null) {
      isRegistered = false;
      isAdmin = false;
    } else {
      isRegistered = true;

      if (!isAdmin(username)) {
        isAdmin = false;
      } else {
        isAdmin = true;

        request.setAttribute("num_users", userStore.getUsersSize());
        request.setAttribute("num_convos", conversationStore.getConversationsSize());
        request.setAttribute("num_messages", messageStore.getMessagesSize());
      }
    }

    request.setAttribute("is_registered", isRegistered);
    request.setAttribute("is_admin", isAdmin);

    request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
    return;
  }

  private boolean isAdmin(String username) {
    ArrayList<String> admins = new ArrayList<String>();
    admins.add("amejia");
    admins.add("shershey");
    admins.add("Rodrigo");
    admins.add("quinykb");
    admins.add("Israel");
    
    return admins.contains(username);
  }
}
