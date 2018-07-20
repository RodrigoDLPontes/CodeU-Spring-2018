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
      <a  href="/userprofile/<%= request.getSession().getAttribute("user")%>">Hello <%= request.getSession().getAttribute("user")%></a>
      <a href="/mentions">Mentions</a>
    <% } else{ %>
      <a href="/login">Login</a>
    <% } %>
    <a href="/about.jsp">About</a>
    <% if(request.getSession().getAttribute("user") != null){ %>
      <a href="/logout">Logout</a>
    <% } %>    
  </nav>

  <div id="container">
    <h1>Admin Page</h1>

    <% Boolean isRegistered = (Boolean) request.getAttribute("is_registered");
      Boolean isAdmin = (Boolean) request.getAttribute("is_admin");

      if (isAdmin) {
    %>
      <ul>

        <li>Users: <%= request.getAttribute("num_users") %></li>
        <li>Conversations: <%= request.getAttribute("num_convos") %></li>
        <li>Messages: <%= request.getAttribute("num_messages") %></li>

      </ul>
    <% } else if (isRegistered) { %>
      <h2>You are not authorized to view this page</h2>
    <% } else { 
        response.sendRedirect("/login");
      }
    %>
  </div>

</body>
</html>
