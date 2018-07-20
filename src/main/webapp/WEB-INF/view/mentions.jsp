<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.Mention" %>
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
<!DOCTYPE html>
<html>
<head>
  <title>Login</title>
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
    
  </nav>
  <div id="container">
  	
    <h2>Mentions</h2>
    <%
    List<Mention> mentions =
      (List<Mention>) request.getAttribute("mentions");
    if(mentions == null || mentions.isEmpty()){
    %>
      <p>These are the people who tagged you in conversations. </p>
    <%
    }
    else{
    %>
      <ul class="mdl-list">
    <%
      for (int i = mentions.size()-1; i >= 0; i--) {
    %>
      
      <li><%= mentions.get(i).toString() %> </li>
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
