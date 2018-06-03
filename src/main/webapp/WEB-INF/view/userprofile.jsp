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
<%@ page import="java.util.List"%>
<%@ page import="codeu.model.data.Conversation"%>
<%@ page import="codeu.controller.UserProfileServlet"%>

<!DOCTYPE html>
<html>
<head>
<title>Profile</title>
<link rel="stylesheet" href="/css/main.css">
</head>
<body>
	<% String userUrl = "/userprofile/"+  request.getSession().getAttribute("user"); %>
	<nav>
		<a id="navTitle" href="/">CodeU Chat App</a> <a href="/conversations">Conversations</a>
		<% if(request.getSession().getAttribute("user") != null){ %>
		<a>Hello <%= request.getSession().getAttribute("user") %>!
		</a> <a href="<%= userUrl %>"> About <%= request.getSession().getAttribute("user")%></a>
		<% } else{ %>
		<a href="/login">Login</a> <a href="/userprofile/">AboutMe</a>
		<% } %>
		<a href="/about.jsp">About</a>
	</nav>




	<!--  Checks if the user is logged  in but Viewing thier own page and alllows them to edit it   -->
	<%  if ((request.getSession().getAttribute("user") != null) && (request.getSession().getAttribute("user").equals(request.getAttribute("user")))) {%>
	<h1 id="profileNameCenter">
		<%= request.getAttribute("user") %>
		you can edit your profile
	</h1>
	<form action="" <%= userUrl %>" method="POST">
		<input type="text" name="aboutme"> <br />
		  <br/><br/>
  <input type="submit" value="Submit">
      </form>

		
		<!--  Checks if the user is logged  and View come else page   -->
		<% } else if(request.getSession().getAttribute("user") != null)  { %>
		<h1 id="profileNameCenter">
			<%= request.getAttribute("user") %>'s profile
		</h1>
		
		<!--  Direcet user to login so they can view profiles   -->
		<% } else { %>
		<a href="/login">Login to view your profile</a>
		<% } %>
	
</body>
</html>
