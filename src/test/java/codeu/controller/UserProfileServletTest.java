package codeu.controller;

import codeu.model.data.AboutMeMessage;

import codeu.model.data.User;
import codeu.model.store.basic.AboutMeMessageStore;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
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
import org.kefirsf.bb.TextProcessor;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class UserProfileServletTest {
	
	  private UserProfileServlet userprofileServlet;
	  private HttpServletRequest mockRequest;
	  private HttpSession mockSession;
	  private HttpServletResponse mockResponse;
	  private RequestDispatcher mockRequestDispatcher;
	  private AboutMeMessageStore mockAboutMeMessageStore;
	  private UserStore mockUserStore;
	  private TextProcessor mockTextProcessor;
	  
	  @Before
	  public void setup() {
		  userprofileServlet = new UserProfileServlet();

	    mockRequest = Mockito.mock(HttpServletRequest.class);
	    mockSession = Mockito.mock(HttpSession.class);
	    Mockito.when(mockRequest.getSession()).thenReturn(mockSession);

	    mockResponse = Mockito.mock(HttpServletResponse.class);
	    mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
	    Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/userprofile.jsp"))
	        .thenReturn(mockRequestDispatcher);
	    
	    mockUserStore = Mockito.mock(UserStore.class);
	    userprofileServlet.setUserStore(mockUserStore);
	    
	    mockTextProcessor = Mockito.mock(TextProcessor.class);
	    userprofileServlet.setTextProcessor(mockTextProcessor);
}
	  
}