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

   <% if (request.getAttribute("unregistered_user") != null) { %>
        <h2><%= request.getAttribute("unregistered_user") %></h2>
    <% } else if (request.getAttribute("non_admin") != null) { %>
        <h2><%= request.getAttribute("non_admin") %></h2>
    <% } else { %>
        <h2><%= request.getAttribute("admin") %></h2>
    <% } %>
  </div>

</body>
</html>
