package codeu.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import codeu.model.data.Conversation;
import codeu.model.data.User;
import codeu.model.store.basic.UserStore;

/**
 * Servlet implementation class UserProfileServlet
 */
@WebServlet("/UserProfileServlet")
public class UserProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public UserProfileServlet() {
        super();
    }
    
    /** Store class that gives access to Users. */
    private UserStore userStore;
    
    /**
     * Sets the UserStore used by this servlet. This function provides a common setup method for use
     * by the test framework or the servlet's init() function.
     */
    void setUserStore(UserStore userStore) {
      this.userStore = userStore;
    }
    /**
     * allows us to generate different URLs for the profile page depending  on the user 
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    	      throws IOException, ServletException {
    
    	String requestUrl = request.getRequestURI();
		String name = requestUrl.substring("/userprofile/".length());
    
    
        request.setAttribute("user", name);
          
        request.getRequestDispatcher("/WEB-INF/view/userprofile.jsp").forward(request, response);
    }
    
  

	

}
