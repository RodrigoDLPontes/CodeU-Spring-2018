package codeu.controller;

import java.io.IOException;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import codeu.model.data.AboutMeMessage;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import codeu.model.data.User;
import codeu.model.store.basic.UserStore;
import codeu.model.store.basic.AboutMeMessageStore;
import codeu.model.store.basic.ConversationStore;
import codeu.model.data.Conversation;

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
	
	 /** Store class that gives access to Conversations. */
	  private ConversationStore conversationStore;
	@Override
	public void init() throws ServletException {
		super.init();
		setUserStore(UserStore.getInstance());
		setAboutMeMessageStore(AboutMeMessageStore.getInstance());
		setConversationStore(ConversationStore.getInstance());
	}

	/*** Sets the UserStore used by this servlet. */
	void setUserStore(UserStore userStore) {
		this.userStore = userStore;
	}

	/*** Sets the AboutMeMessageStore used by this servlet. */
	void setAboutMeMessageStore(AboutMeMessageStore aboutmemessageStore) {
		this.aboutmemessageStore = aboutmemessageStore;
	}
	 void setConversationStore(ConversationStore conversationStore) {
		    this.conversationStore = conversationStore;
		  }

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		

		String requestUrl = request.getRequestURI();
		String currentProfile = requestUrl.substring("/userprofile/".length());
		
		User user = userStore.getUser(currentProfile);
		
		
		  
		request.setAttribute("user", currentProfile);
		    
		  List<AboutMeMessage> aboutmemessages =  aboutmemessageStore.getAllAboutMeMessages();
		    request.setAttribute("aboutmemessage", aboutmemessages);
		    
		/*
		   User user= aboutmemessageStore.getAboutMeMessagesByUser(name);
		    if (conversation == null) {
		      // couldn't find conversation, redirect to conversation list
		      System.out.println("Conversation was null: " + conversationTitle);
		      response.sendRedirect("/conversations");
		      return;
		    }
		
		/*
		
		  UUID authorId = user.getId();
	List<AboutMeMessage> aboutmemessage = aboutmemessageStore.getAboutMeMessagesByUser(authorId);
//	    request.setAttribute("aboutmemessage", aboutmemessage);
 * */
 
		request.getRequestDispatcher("/WEB-INF/view/userprofile.jsp").forward(request, response);
	}



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
		String Profile = requestUrl.substring("/userprofile/".length());
		request.setAttribute("user", Profile);

		String User = (String) request.getSession().getAttribute("user");
		if (User != null) {
			String aboutMeContent = request.getParameter("aboutme");
			// this removes any HTML from the message content
			aboutMeContent = Jsoup.clean(aboutMeContent, Whitelist.none());
			//response.getOutputStream().println("<h1>" + aboutMeContent + "</h1>");
			System.out.println(aboutMeContent);

			AboutMeMessage aboutmemessage = new AboutMeMessage(UUID.randomUUID(), user.getId(), aboutMeContent,
					Instant.now());

			aboutmemessageStore.addAboutMeMessage(aboutmemessage);
			// redirect to a GET request
			response.sendRedirect("/userprofile/"+ username);

		}
	}
}
