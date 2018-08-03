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
<% GeneralTimingFilter filter = new GeneralTimingFilter(Type.CHAT_JSP); %>

<%@ page import="java.util.List" %>
<%@ page import="java.util.LinkedHashSet" %>
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.Message" %>
<%@ page import="codeu.model.data.User" %>
<%@ page import="codeu.model.store.basic.UserStore" %>
<%
Conversation conversation = (Conversation) request.getAttribute("conversation");
List<Message> messages = (List<Message>) request.getAttribute("messages");
LinkedHashSet<User> members = (LinkedHashSet<User>) request.getAttribute("members");
		  /*** The unique Url that is generated for each Users about me page  */
		
%>

<!DOCTYPE html>
<html>
<head>
  <title><%= conversation.getTitle() %></title>
  <link rel="stylesheet" href="/css/main.css" type="text/css">

  <style>
    #chat {
      background-color: white;
      height: 500px;
      overflow-y: scroll;
      float: left;
      width: 580px;
    }

    #members {
      background-color: white;
      height: 500px;
      margin-left: 600px;
      width: 200px;
      overflow-y: scroll;
    }

    #invite {
      background-color: white;
      float: bottom;
    }
  </style>

  <script>
    // scroll the chat div to the bottom
    function scrollChat() {
      var chatDiv = document.getElementById('chat');
      chatDiv.scrollTop = chatDiv.scrollHeight;
    };
  </script>
</head>
<body onload="scrollChat()">


  <nav>
    <a id="navTitle" href="/">CodeU Chat App</a>
    <a href="/conversations">Conversations</a>
      <% if (request.getSession().getAttribute("user") != null) { %>
      <a  href="/userprofile/<%= request.getSession().getAttribute("user")%>">Hello <%= request.getSession().getAttribute("user") %>!</a>
    <a href="/mentions">Mentions</a>
    <% } else { %>
      <a href="/login">Login</a>
    <% } %>
    <a href="/about.jsp">About</a>
    <% if(request.getSession().getAttribute("user") != null){ %>
      <a href="/logout">Logout</a>
    <% } %>    
  </nav>

  <div id="container">
    <% if(request.getAttribute("error") != null){ %>
        <h2 style="color:red"><%= request.getAttribute("error") %></h2>
    <% } %>

    <h1><%= conversation.getTitle() %>
      <a href="" style="float: right">&#8635;</a></h1>

    <hr/>

    <div id="chat">
      <ul>
     <%
      for (Message message : messages) {
        String author = UserStore.getInstance()
          .getUser(message.getAuthorId()).getName();
          
        String url = "/userprofile/" + author;
    %>

		<li><strong> <a href=<%= url %>> <%= author %>:</strong> </a> <%= message.getContent() %>

		   <% if (UserStore.getInstance().getUser(author).getName().equals(request.getSession().getAttribute("user"))) { %>
			 <form action="/chat/<%= conversation.getTitle() %>" method="POST">
			 	<button class="deleteButton" type="submit">Delete</button>
				<input type="hidden" name="delete" value="true"> 
				<input type="hidden" name="messageId" value="<%= message.getId() %>">
			</form> <% } %></li>
				<%
      }
    %>
      </ul>
    </div>

    <div id="members">
      <p style="text-align: center"><strong>Members</strong></p>
      <ul>
        <% for (User user : members) {
            String username = user.getName();
        %>

        <li><%= username %></li>

        <%
          }
        %>
      </ul>
    </div>

    <hr/>

    <% if (request.getSession().getAttribute("user") != null) { %>
    <form action="/chat/<%= conversation.getTitle() %>" method="POST">
        <input type="text" name="message">
        <br/>
        <button type="submit">Send</button>
    </form>
    <form action="/chat/<%= conversation.getTitle() %>" method="POST">
      <input type="text" name="member_name">
      <br/>
      <button type="submit">Invite member</button>
    </form>
    <% } else { %>
      <p><a href="/login">Login</a> to send a message.</p>
    <% } %>

    <hr/>

  </div>

</body>
</html>

<% filter.finish(); %>
