package codeu.controller;

import codeu.model.data.AboutMeMessage;
import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.basic.AboutMeMessageStore;
import codeu.model.store.basic.ConversationStore;
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
  private AboutMeMessageStore aboutmemessageStore;
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
    Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/userprofile.jsp")).thenReturn(mockRequestDispatcher);

    mockAboutMeMessageStore = Mockito.mock(AboutMeMessageStore.class);
    userprofileServlet.setAboutMeMessageStore(mockAboutMeMessageStore);

    mockUserStore = Mockito.mock(UserStore.class);
    userprofileServlet.setUserStore(mockUserStore);

    mockTextProcessor = Mockito.mock(TextProcessor.class);
    userprofileServlet.setTextProcessor(mockTextProcessor);
  }

  @Test
  public void testDoGet() throws IOException, ServletException {
    Mockito.when(mockRequest.getRequestURI()).thenReturn("/userprofile/test_username");
    // final UUID Author = UUID.randomUUID();

    // This is person who write the about me message and who name appears in the Url
    // for the aboutme page
    List<AboutMeMessage> fakeAboutMeMessageList = new ArrayList<>();
    fakeAboutMeMessageList
        .add(new AboutMeMessage(UUID.randomUUID(), UUID.randomUUID(), "test_aboutmemessage", Instant.now()));
    Mockito.when(mockAboutMeMessageStore.getAllAboutMeMessages()).thenReturn(fakeAboutMeMessageList);

    userprofileServlet.doGet(mockRequest, mockResponse);

  }

  @Test
  public void testDoPost_InvalidUser() throws IOException, ServletException {
    Mockito.when(mockSession.getAttribute("user")).thenReturn("test_username");
    Mockito.when(mockUserStore.getUser("test_username")).thenReturn(null);

    userprofileServlet.doPost(mockRequest, mockResponse);

    Mockito.verify(mockAboutMeMessageStore, Mockito.never()).addAboutMeMessage(Mockito.any(AboutMeMessage.class));
    Mockito.verify(mockResponse).sendRedirect("/login");
  }

}