package codeu.controller;

import codeu.model.data.Conversation;
import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//public class ActivityFeedServlet extends HttpServlet {
public class ActivityFeedServlet extends HttpServlet {
	/** Store class that gives access to Users. */
	  private UserStore userStore;

	  /** Store class that gives access to Conversations. */
	  private ConversationStore conversationStore;

	  /**
	   * Set up state for handling conversation-related requests. This method is only called when
	   * running in a server, not when running in a test.
	   */
	  @Override
	  public void init() throws ServletException {
	    super.init();
	    setUserStore(UserStore.getInstance());
	    setConversationStore(ConversationStore.getInstance());
	  }

	  /**
	   * Sets the UserStore used by this servlet. This function provides a common setup method for use
	   * by the test framework or the servlet's init() function.
	   */
	  void setUserStore(UserStore userStore) {
	    this.userStore = userStore;
	  }

	  /**
	   * Sets the ConversationStore used by this servlet. This function provides a common setup method
	   * for use by the test framework or the servlet's init() function.
	   */
	  void setConversationStore(ConversationStore conversationStore) {
	    this.conversationStore = conversationStore;
	  }

	  /**
	   * This function fires when a user navigates to the conversations page. It gets all of the
	   * conversations from the model and forwards to conversations.jsp for rendering the list.
	   */
	  @Override
	  public void doGet(HttpServletRequest request, HttpServletResponse response)
	      throws IOException, ServletException {
//	    List<Conversation> conversations = conversationStore.getAllConversations();
	    request.setAttribute("icecream", "Sea-Salt is a weird Flavor");
	    request.getRequestDispatcher("/WEB-INF/view/activity_feed.jsp").forward(request, response);
	  }

}
