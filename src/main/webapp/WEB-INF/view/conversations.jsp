<%--
  Copyright 2017 Google Inc.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
--%>
<%@ page import="codeu.model.data.Statistic.Type" %>
<%@ page import="codeu.service.GeneralTimingFilter" %>
<% GeneralTimingFilter filter = new GeneralTimingFilter(Type.CONVERSATIONS_JSP); %>

<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="java.util.LinkedHashSet" %>

<!DOCTYPE html>
<html>
<head>
  <title>Conversations</title>
  <link rel="stylesheet" href="/css/main.css">
</head>
<body>

  <nav>
    <a id="navTitle" href="/">CodeU Chat App</a>
    <a href="/conversations">Conversations</a>
    <% String username = (String) request.getSession().getAttribute("user"); %>
    <% if(username != null) { %>
      <a  href="/userprofile/<%= request.getSession().getAttribute("user")%>">Hello <%= username %></a>
      <a href="/mentions">Mentions</a>
    <% } else { %>
      <a href="/login">Login</a>
    
    <% } %>
    <a href="/about.jsp">About</a>
    <% if(username != null) { %>
      <a href="/logout">Logout</a>
    <% } %>    
  </nav>


  <div id="container">

    <% if(request.getAttribute("error") != null){ %>
        <h2 style="color:red"><%= request.getAttribute("error") %></h2>
    <% } %>

    <% if(username != null) { %>
      <h1>New Conversation</h1>
      <form action="/conversations" method="POST">
          <div class="form-group">
            <label class="form-control-label">Title:</label>
          <input type="text" name="conversationTitle">
        </div>

        <button type="submit">Create</button>
      </form>

      <hr/>
      <h1>Conversations</h1>

      <%
      LinkedHashSet<Conversation> conversations =
        (LinkedHashSet<Conversation>) request.getAttribute("conversations");
      if(conversations == null || conversations.isEmpty()){
      %>
        <p>You have no conversations to view.</p>
      <%
      } else {
      %>
        <ul class="mdl-list">
      <%
        for(Conversation conversation : conversations){
      %>
        <li><a href="/chat/<%= conversation.getTitle() %>">
          <%= conversation.getTitle() %></a></li>
      <%
        }
      %>
        </ul>
      <%
      }
    } else { 
      %>
      <p>You must <a href="/login">login</a> to view conversations.</p>
    <%
    }
    %>

    <hr/>
  </div>
</body>
</html>

<% filter.finish(); %>
