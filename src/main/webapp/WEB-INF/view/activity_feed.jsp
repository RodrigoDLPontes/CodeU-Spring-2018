<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
  <title>Activity Feed</title>
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

    <h2>Activity</h2>
    <%
    List<String> activities =
      (List<String>) request.getAttribute("activities");
    if(activities == null || activities.isEmpty()){
    %>
      <p>Create a conversation to get started.</p>
    <%
    }
    else{
    %>
      <ul class="mdl-list">
    <%
      for(String activity : activities){
    %>
      
      <li><%= activity %> </li>
    <%
      }
    %>
      </ul>
    <%
    }
    %>
  </div>


  
  
</body>
</html>