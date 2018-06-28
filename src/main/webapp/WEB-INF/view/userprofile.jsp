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
<%@ page import="codeu.model.data.AboutMeMessage"%>
<%@ page import="codeu.model.data.User"%>
<%@ page import="codeu.model.store.basic.UserStore"%>
<%
	// Creats a list of all   to all aboutmemessages
	List<AboutMeMessage> aboutmemessages = (List<AboutMeMessage>) request.getAttribute("aboutmemessage");
%>

<!DOCTYPE html>
<html>
<head>
<title>Profile</title>
<link rel="stylesheet" href="/css/main.css">
</head>
<body>
	<%
	   /*** The unique Url that is generated for each Users about me page  */
		String userUrl = "/userprofile/" + request.getSession().getAttribute("user"); 
		// This is the currrent user that is logged.in
		String currenLoginedInUser = request.getSession().getAttribute("user").toString();
		// This is the  user  who's profile is getting looked at by another user
		String viewAnotherUserProfile = request.getAttribute("user").toString();
	%>
	<nav>
		<a id="navTitle" href="/">CodeU Chat App</a> <a href="/conversations">Conversations</a>
		<%
			if (request.getSession().getAttribute("user") != null) {
		%>
		<a  href="/userprofile/<%= request.getSession().getAttribute("user")%>">Hello <%= request.getSession().getAttribute("user")%></a>
		<%
			} else {
		%>
		<a href="/login">Login</a> 
		<%
			}
		%>
		<a href="/about.jsp">About</a>
	</nav>
	
	<!--  Checks if the user is logged  in but Viewing thier own page and alllows them to edit it   -->
	<%
		if ((request.getSession().getAttribute("user") != null)
				&& (request.getSession().getAttribute("user").equals(request.getAttribute("user")))) {
	%>
	<h1 id="profileNameCenter">
		<%=request.getAttribute("user")%>
		tell us all about yourself
	</h1>

	<ul class="mdl-list">
		<%
			for (AboutMeMessage aboutmemessage : aboutmemessages) {
					String author = UserStore.getInstance().getUser(aboutmemessage.getAuthorId()).getName();
					if (author.equals(currenLoginedInUser)) {
		%>
		<li><strong> <%=author%>:
		</strong> </a> <%=aboutmemessage.getContent()%></li>
		<%
			}
		%>
		<%
			}
		%>
	</ul>
	<form action="" <%=userUrl%>" method="POST">
		<input type="text" name="aboutme"> <br /> <br /> <br /> <input
			type="submit" value="Submit">
	</form>


	<!--  Checks if the user is logged  and View come else page   -->
	<%
		} else if (request.getSession().getAttribute("user") != null) {
	%>
	<h1 id="profileNameCenter">
		<%=request.getAttribute("user")%>'s profile Read all about them
	</h1>

	<ul class="mdl-list">
		<%
			for (AboutMeMessage aboutmemessage : aboutmemessages) {
					String author = UserStore.getInstance().getUser(aboutmemessage.getAuthorId()).getName();
					if (author.equals(viewAnotherUserProfile)) {
		%>
		<li><strong> <%=author%>:
		</strong> </a> <%=aboutmemessage.getContent()%></li>
		<%
			}
		%>
		<%
			}
		%>
	</ul>
	<!--  Direcet user to login so they can view profiles   -->
	<%
		} else {
	%>
	<a href="/login">Login to view your profile</a>
	<%
		}
	%>
</body>
</html>
