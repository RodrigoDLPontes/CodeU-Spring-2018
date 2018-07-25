// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package codeu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet class responsible for the statistics page. */
public class StatisticsServlet extends HttpServlet {

  /**
   * Set up state for handling statistics-related requests. This method is only called when running
   * in a server, not when running in a test.
   */
  @Override
  public void init() throws ServletException {
    super.init();
  }

  /**
   * This function fires when a user requests the /statistics URL. It simply forwards the request to
   * the appropriate statistics page.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    String requestUri = request.getRequestURI();
    String statisticsTitle = requestUri.substring("/statistics/".length());
    switch(statisticsTitle) {
      default:
      case "chat-servlet":
        request.getRequestDispatcher("/WEB-INF/view/statistics/chat-servlet.jsp")
            .forward(request, response);
        break;
      case "conversation-servlet":
        request.getRequestDispatcher("/WEB-INF/view/statistics/conversation-servlet.jsp")
            .forward(request, response);
        break;
      case "login-servlet":
        request.getRequestDispatcher("/WEB-INF/view/statistics/login-servlet.jsp")
            .forward(request, response);
        break;
      case "register-servlet":
        request.getRequestDispatcher("/WEB-INF/view/statistics/register-servlet.jsp")
            .forward(request, response);
        break;
      case "conversation-store":
        request.getRequestDispatcher("/WEB-INF/view/statistics/conversation-store.jsp")
            .forward(request, response);
        break;
      case "message-store":
        request.getRequestDispatcher("/WEB-INF/view/statistics/message-store.jsp")
            .forward(request, response);
        break;
      case "user-store":
        request.getRequestDispatcher("/WEB-INF/view/statistics/user-store.jsp")
            .forward(request, response);
        break;
      case "persistent-data-store":
        request.getRequestDispatcher("/WEB-INF/view/statistics/persistent-data-store.jsp")
            .forward(request, response);
        break;
      case "jsp-pages":
        request.getRequestDispatcher("/WEB-INF/view/statistics/jsp-pages.jsp")
            .forward(request, response);
        break;
    }
  }
}
