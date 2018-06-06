package codeu.controller;

import codeu.model.data.Activity;
import codeu.model.store.basic.ActivityStore;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.LinkedList;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//public class ActivityFeedServlet extends HttpServlet {
public class ActivityFeedServlet extends HttpServlet {
	  
	  /** Store class that gives access to Activities. */
	  private ActivityStore activityStore;

	  /**
	   * Set up state for handling conversation-related requests. This method is only called when
	   * running in a server, not when running in a test.
	   */
	  @Override
	  public void init() throws ServletException {
	    super.init();
	    setActivityStore(ActivityStore.getInstance());
	  }

	  void setActivityStore(ActivityStore activityStore) {
		  this.activityStore = activityStore;
	  }
	  
	  /**
	   * This function fires when a user navigates to the conversations page. It gets all of the
	   * conversations from the model and forwards to conversations.jsp for rendering the list.
	   */
	  @Override
	  public void doGet(HttpServletRequest request, HttpServletResponse response)
	      throws IOException, ServletException {
	    List<Activity> activities = this.activityStore.getAllActivities();
	    request.setAttribute("activities", activities);
	    request.getRequestDispatcher("/WEB-INF/view/activity_feed.jsp").forward(request, response);
	  }

}
