<%@ page import="java.util.Map" %>
<%@ page import="java.util.LinkedHashMap" %>
<%@ page import="codeu.controller.AdminServlet" %>

<!DOCTYPE html>
<html>
<head>
  <title>Admin Page</title>
  <link rel="stylesheet" href="/css/main.css">
</head>
<body>

  <nav>
    <a id="navTitle" href="/">CodeU Chat App</a>
    <a href="/conversations">Conversations</a>
    <% if(request.getSession().getAttribute("user") != null){ %>
      <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
    <% } else{ %>
      <a href="/login">Login</a>
    <% } %>
    <a href="/about.jsp">About</a>
  </nav>

  <div id="container">
    <h1>Admin Page</h1>

    <% AdminServlet.LoginState loginState = (AdminServlet.LoginState) request.getAttribute("login_state");

       switch (loginState) {
        case UNREGISTERED:
          response.sendRedirect("/login");
          break;
        case REGISTERED:
    %>    

          <h2>You are not authorized to view this page</h2>

    <%    break; 
        case ADMIN:
          Map<String, String> stats = (Map<String, String>) request.getAttribute("stats"); 
    %>
          <ul>

            <% for (String key : stats.keySet()) { %>
              <li><%= key %>: <%= stats.get(key) %></li>
            <% } %>

          </ul>
    <%    break;
      }
    %>
  </div>

</body>
</html>
