package codeu.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.mockito.Mockito;
import codeu.model.store.basic.UserStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.ConversationStore;
import codeu.controller.AdminServlet.LoginState;

public class AdminServletTest {

  private AdminServlet adminServlet;
  private HttpServletRequest mockRequest;
  private HttpSession mockSession;
  private HttpServletResponse mockResponse;
  private RequestDispatcher mockRequestDispatcher;
  private ConversationStore mockConversationStore;
  private MessageStore mockMessageStore;
  private UserStore mockUserStore;

  @Before
  public void setup() {
    adminServlet = new AdminServlet();

    mockRequest = Mockito.mock(HttpServletRequest.class);
    mockSession = Mockito.mock(HttpSession.class);
    Mockito.when(mockRequest.getSession()).thenReturn(mockSession);
    
    mockResponse = Mockito.mock(HttpServletResponse.class);
    mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);

    mockConversationStore = Mockito.mock(ConversationStore.class);
    adminServlet.setConversationStore(mockConversationStore);

    mockMessageStore = Mockito.mock(MessageStore.class);
    adminServlet.setMessageStore(mockMessageStore);

    mockUserStore = Mockito.mock(UserStore.class);
    adminServlet.setUserStore(mockUserStore);

    Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/admin.jsp"))
        .thenReturn(mockRequestDispatcher);
  }

  @Test
  public void testDoGet_UnregisteredUserReturnsDeniedAccess() throws IOException, ServletException {
    Mockito.when(mockSession.getAttribute("user")).thenReturn(null);
      
    adminServlet.doGet(mockRequest, mockResponse);

    Mockito.verify(mockRequest).setAttribute("login_state", LoginState.UNREGISTERED);
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoGet_NonAdminUserReturnsDeniedAccess() throws IOException, ServletException {
    Mockito.when(mockSession.getAttribute("user")).thenReturn("test_username");
    
    adminServlet.doGet(mockRequest, mockResponse);

    Mockito.verify(mockRequest).setAttribute("login_state", LoginState.REGISTERED);
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoGet_AdminUserReturnsCanAccess() throws IOException, ServletException {
    Mockito.when(mockSession.getAttribute("user")).thenReturn("amejia");

    Mockito.when(mockUserStore.size()).thenReturn(0);
    Mockito.when(mockConversationStore.size()).thenReturn(0);
    Mockito.when(mockMessageStore.size()).thenReturn(0);
    
    adminServlet.doGet(mockRequest, mockResponse);

    Mockito.verify(mockRequest).setAttribute("login_state", LoginState.ADMIN);
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }
}
