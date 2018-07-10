<!DOCTYPE html>
<html>
<head>
  <title>Request Page</title>
  <link rel="stylesheet" href="/css/main.css">
</head>
<body>

  <nav>
    <a id="navTitle" href="/">CodeU Chat App</a>
    <a href="/conversations">Conversations</a>
    <% if(request.getSession().getAttribute("user") != null){ %>
      <a  href="/userprofile/<%= request.getSession().getAttribute("user")%>">Hello <%= request.getSession().getAttribute("user")%></a>
    <% } else{ %>
      <a href="/login">Login</a>
    <% } %>
    <a href="/about.jsp">About</a>
    <% if(request.getSession().getAttribute("user") != null){ %>
      <a href="/logout">Logout</a>
    <% } %>    
  </nav>

  <div id="container">
    <h1>Request Page</h1>
  </div>

</body>
</html>