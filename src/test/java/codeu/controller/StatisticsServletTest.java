package codeu.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.notNull;

public class StatisticsServletTest {

  private StatisticsServlet statisticsServlet;
  private HttpServletRequest mockRequest;
  private HttpServletResponse mockResponse;
  private RequestDispatcher mockRequestDispatcher;

  @Before
  public void setup() {
    statisticsServlet = new StatisticsServlet();
    mockRequest = Mockito.mock(HttpServletRequest.class);
    mockResponse = Mockito.mock(HttpServletResponse.class);
    mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
    Mockito.when(mockRequest.getRequestDispatcher(notNull())).thenReturn(mockRequestDispatcher);
  }

  @Test
  public void testDoGet_emptyUriSuffix() throws IOException, ServletException {
    String uri = "";
    Mockito.when(mockRequest.getRequestURI()).thenReturn(getRequestUri(uri));
    statisticsServlet.doGet(mockRequest, mockResponse);
    // We want to send to the default chat servlet page
    Mockito.verify(mockRequest).getRequestDispatcher(getRequestDispatcherUri("chat-servlet"));
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoGet_invalidUriSuffix() throws IOException, ServletException {
    String uri = "not-a-page";
    Mockito.when(mockRequest.getRequestURI()).thenReturn(getRequestUri(uri));
    statisticsServlet.doGet(mockRequest, mockResponse);
    // We want to send to the default chat servlet page
    Mockito.verify(mockRequest).getRequestDispatcher(getRequestDispatcherUri("chat-servlet"));
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoGet_chatServlet() throws IOException, ServletException {
    String uri = "chat-servlet";
    Mockito.when(mockRequest.getRequestURI()).thenReturn(getRequestUri(uri));
    statisticsServlet.doGet(mockRequest, mockResponse);
    Mockito.verify(mockRequest).getRequestDispatcher(getRequestDispatcherUri(uri));
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoGet_conversationServlet() throws IOException, ServletException {
    String uri = "conversation-servlet";
    Mockito.when(mockRequest.getRequestURI()).thenReturn(getRequestUri(uri));
    statisticsServlet.doGet(mockRequest, mockResponse);
    Mockito.verify(mockRequest).getRequestDispatcher(getRequestDispatcherUri(uri));
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoGet_loginServlet() throws IOException, ServletException {
    String uri = "login-servlet";
    Mockito.when(mockRequest.getRequestURI()).thenReturn(getRequestUri(uri));
    statisticsServlet.doGet(mockRequest, mockResponse);
    Mockito.verify(mockRequest).getRequestDispatcher(getRequestDispatcherUri(uri));
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoGet_registerServlet() throws IOException, ServletException {
    String uri = "register-servlet";
    Mockito.when(mockRequest.getRequestURI()).thenReturn(getRequestUri(uri));
    statisticsServlet.doGet(mockRequest, mockResponse);
    Mockito.verify(mockRequest).getRequestDispatcher(getRequestDispatcherUri(uri));
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoGet_conversationStore() throws IOException, ServletException {
    String uri = "conversation-store";
    Mockito.when(mockRequest.getRequestURI()).thenReturn(getRequestUri(uri));
    statisticsServlet.doGet(mockRequest, mockResponse);
    Mockito.verify(mockRequest).getRequestDispatcher(getRequestDispatcherUri(uri));
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoGet_messageStore() throws IOException, ServletException {
    String uri = "message-store";
    Mockito.when(mockRequest.getRequestURI()).thenReturn(getRequestUri(uri));
    statisticsServlet.doGet(mockRequest, mockResponse);
    Mockito.verify(mockRequest).getRequestDispatcher(getRequestDispatcherUri(uri));
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoGet_userStore() throws IOException, ServletException {
    String uri = "user-store";
    Mockito.when(mockRequest.getRequestURI()).thenReturn(getRequestUri(uri));
    statisticsServlet.doGet(mockRequest, mockResponse);
    Mockito.verify(mockRequest).getRequestDispatcher(getRequestDispatcherUri(uri));
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }
  
  @Test
  public void testDoGet_persistentDataStore() throws IOException, ServletException {
    String uri = "persistent-data-store";
    Mockito.when(mockRequest.getRequestURI()).thenReturn(getRequestUri(uri));
    statisticsServlet.doGet(mockRequest, mockResponse);
    Mockito.verify(mockRequest).getRequestDispatcher(getRequestDispatcherUri(uri));
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoGet_jspPages() throws IOException, ServletException {
    String uri = "jsp-pages";
    Mockito.when(mockRequest.getRequestURI()).thenReturn(getRequestUri(uri));
    statisticsServlet.doGet(mockRequest, mockResponse);
    Mockito.verify(mockRequest).getRequestDispatcher(getRequestDispatcherUri(uri));
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  private static String getRequestUri(String suffix) {
    return "/statistics/" + suffix;
  }

  private static String getRequestDispatcherUri(String suffix) {
    return "/WEB-INF/view/statistics/" + suffix + ".jsp";
  }
}
