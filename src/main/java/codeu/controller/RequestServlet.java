package codeu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestServlet extends HttpServlet {
  
  @Override
  public void init() throws ServletException {
    super.init();
  }

  /**
   * Serves the requests page for the logged in user. Can access the page directly from /request url
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    request.getRequestDispatcher("/WEB-INF/view/request.jsp").forward(request, response);
    return;
  }
}
