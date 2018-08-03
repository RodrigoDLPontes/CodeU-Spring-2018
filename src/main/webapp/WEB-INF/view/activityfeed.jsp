<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.Activity" %>
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
      <a href="/mentions">Mentions</a>
    <% } else{ %>
      <a href="/login">Login</a>
    <% } %>
    <a href="/about.jsp">About</a>
  </nav>
  <div id="container">

    <h2>Activities</h2>
    <%
    List<Activity> activities =
      (List<Activity>) request.getAttribute("activities");
    if(activities == null || activities.isEmpty()){
    %>
      <p>Here is what is happening on your feed.</p>
    <%
    }
    else{
    %>
      <ul class="mdl-list">
    <%
      for (int i = activities.size()-1; i >= 0; i--) {
    %>
      
      <li><%= activities.get(i).toString() %> </li>
    <%
      }
    %>
      </ul>
    <%
    }
    %>
  </div>


  
  
</body>
