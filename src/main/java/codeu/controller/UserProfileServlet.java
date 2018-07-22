package codeu.controller;

import codeu.model.data.AboutMeMessage;

import codeu.model.data.User;
import codeu.model.store.basic.AboutMeMessageStore;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.kefirsf.bb.BBProcessorFactory;
import org.kefirsf.bb.TextProcessor;

/**
 * Servlet implementation class UserProfileServlet
 */
@WebServlet("/UserProfileServlet")
public class UserProfileServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /** Store class that gives access to Users. */
  private UserStore userStore;

  /** Store class that gives access to AboutMe. */
  private AboutMeMessageStore aboutmemessageStore;

  /** TextProcessor for parsing BBCode */
  private TextProcessor textProcessor;

  @Override
  public void init() throws ServletException {
    super.init();
    setUserStore(UserStore.getInstance());
    setAboutMeMessageStore(AboutMeMessageStore.getInstance());
    setTextProcessor(BBProcessorFactory.getInstance().create());
  }

  /*** Sets the UserStore used by this servlet. */
  void setUserStore(UserStore userStore) {
    this.userStore = userStore;
  }

  /*** Sets the AboutMeMessageStore used by this servlet. */
  void setAboutMeMessageStore(AboutMeMessageStore aboutmemessageStore) {
    this.aboutmemessageStore = aboutmemessageStore;
  }

  /**
   * Sets the TextProcessor used by this servlet. This function provides a common
   * setup method for use by the test framework or the servlet's init() function.
   */
  void setTextProcessor(TextProcessor textProcessor) {
    this.textProcessor = textProcessor;
  }

  /**
   * This function fires when a user navigates to the UserProfile page. It gets
   * the all information that wrote in the about me section the URL, finds the
   * corresponding UserProfile page, and fetches the all AboutMe messages the user
   * has wrote.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    String requestUrl = request.getRequestURI();
    String currentProfile = requestUrl.substring("/userprofile/".length());
    String username = (String) request.getSession().getAttribute("user");
    if (username == null) {
      // user is not logged in, don't let them add a message
      response.sendRedirect("/login");
      return;
    }

    User user = userStore.getUser(username);
         if (user == null) {
         // user was not found, don't let them add a AboutMeMessage
         response.sendRedirect("/login");
        return;
    }

    List<AboutMeMessage> aboutmemessages = aboutmemessageStore.getAllAboutMeMessages();

    request.setAttribute("aboutmemessage", aboutmemessages);
    request.setAttribute("user", currentProfile);
    request.getRequestDispatcher("/WEB-INF/view/userprofile.jsp").forward(request, response);
  }

  /**
   * This function fires when a user submits the form on the UserProfile page. It
   * gets the logged-in username from the session, and the About Me message from
   * the submitted form data. It creates a new About Me Message from that data,
   * adds it to the model, and then redirects back to the User Profile page with
   * all of the user about me messages
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    String username = (String) request.getSession().getAttribute("user");
       if (username == null) {
      // user is not logged in, don't let them add a message
         response.sendRedirect("/login");
         return;
    }

    User user = userStore.getUser(username);
       if (user == null) {
      // user was not found, don't let them add a AboutMeMessage
        response.sendRedirect("/login");
        return;
    }

    String requestUrl = request.getRequestURI();

    boolean shouldDelete = Boolean.valueOf(request.getParameter("deleteAboutme"));
    if (shouldDelete) {
      aboutmemessageStore.deleteAboutMeMessage(
          aboutmemessageStore.getAboutMeMessage(UUID.fromString(request.getParameter("aboutmemessageId"))));
      response.sendRedirect("/userprofile/" + username);
      return;
    }
    String aboutMeContent = request.getParameter("aboutme");

    // this removes any HTML from the message content
    String cleanedAboutMeContent = Jsoup.clean(aboutMeContent, Whitelist.none());

    // this parses BBCode tags to equivalent HTML tags
    String leanedAboutMeAndBBMessageContent = textProcessor.process(cleanedAboutMeContent);

    AboutMeMessage aboutmemessage = new AboutMeMessage(
        UUID.randomUUID(), 
        user.getId(),
        leanedAboutMeAndBBMessageContent, 
        Instant.now());

    aboutmemessageStore.addAboutMeMessage(aboutmemessage);
    // redirect to a GET request
    response.sendRedirect("/userprofile/" + username);

  }

}
