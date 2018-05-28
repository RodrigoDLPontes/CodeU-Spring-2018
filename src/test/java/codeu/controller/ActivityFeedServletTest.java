package codeu.controller;

import codeu.model.data.Conversation;
import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;


public class ActivityFeedServletTest {

	  private ActivityFeedServlet activityFeedServlet;
	  private HttpServletRequest mockRequest;
	  private HttpSession mockSession;
	  private HttpServletResponse mockResponse;
	  private RequestDispatcher mockRequestDispatcher;
	  private ConversationStore mockConversationStore;
	  private UserStore mockUserStore;
	  
	  @Before
	  public void setup() {
		  activityFeedServlet = new ActivityFeedServlet();

	    mockRequest = Mockito.mock(HttpServletRequest.class);
	    mockSession = Mockito.mock(HttpSession.class);
	    Mockito.when(mockRequest.getSession()).thenReturn(mockSession);

	    mockResponse = Mockito.mock(HttpServletResponse.class);
	    mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
	    Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/conversations.jsp"))
	        .thenReturn(mockRequestDispatcher);

	    mockConversationStore = Mockito.mock(ConversationStore.class);
	    activityFeedServlet.setConversationStore(mockConversationStore);

	    mockUserStore = Mockito.mock(UserStore.class);
	    activityFeedServlet.setUserStore(mockUserStore);
	  }

	  @Test
	  public void testDoGet() throws IOException, ServletException {
	    List<Conversation> fakeConversationList = new ArrayList<>();
	    fakeConversationList.add(
	        new Conversation(UUID.randomUUID(), UUID.randomUUID(), "test_conversation", Instant.now()));
	    Mockito.when(mockConversationStore.getAllConversations()).thenReturn(fakeConversationList);

	    activityFeedServlet.doGet(mockRequest, mockResponse);
	    
	    List<String> activities = new LinkedList<>();
	    for(Conversation conversation : fakeConversationList){
	    	String activity = conversation.getCreationTime().toString() + ": " + mockUserStore.getUser(conversation.getOwnerId()).getName() + " created a new conversation: " + conversation.getTitle();
	    	activities.add(activity);
	    }
	    
	    Mockito.verify(mockRequest).setAttribute("activities", activities);
	    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
	  }
}
