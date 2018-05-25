package codeu.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import codeu.model.store.basic.UserStore;

public class AdminServletTest {

  private AdminServlet adminServlet;
  private HttpServletRequest mockRequest;
  private HttpSession mockSession;
  private HttpServletResponse mockResponse;
  private RequestDispatcher mockRequestDispatcher;

  @Before
  public void setup() {
    adminServlet = new AdminServlet();

    mockRequest = Mockito.mock(HttpServletRequest.class);
    mockSession = Mockito.mock(HttpSession.class);
    Mockito.when(mockRequest.getSession()).thenReturn(mockSession);
    
    mockResponse = Mockito.mock(HttpServletResponse.class);
    mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
    Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/admin.jsp"))
        .thenReturn(mockRequestDispatcher);
  }

  @Test
  public void testDoGet_UnregisteredUserReturnsDeniedAccess() throws IOException, ServletException {
    Mockito.when(mockSession.getAttribute("user")).thenReturn(null);
      
    adminServlet.doGet(mockRequest, mockResponse);

    Mockito.verify(mockRequest).setAttribute("unregistered_user", 
      "You must login and be an admin to view this page.");
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoGet_NonAdminReturnsDeniedAccess() throws IOException, ServletException {
    Mockito.when(mockSession.getAttribute("user")).thenReturn("test_username");
    
    adminServlet.doGet(mockRequest, mockResponse);

    Mockito.verify(mockRequest).setAttribute(
        "non_admin", "You are not authorized to view this page");
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoGet_AdminUserReturnsCanAccess() throws IOException, ServletException {
    Mockito.when(mockSession.getAttribute("user")).thenReturn("amejia");
    
    adminServlet.doGet(mockRequest, mockResponse);

    Mockito.verify(mockRequest).setAttribute("admin", "Hi amejia! Welcome to the admin page!");
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }
}
