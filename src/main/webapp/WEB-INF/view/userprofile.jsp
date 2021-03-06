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
<%@ page import="codeu.model.store.basic.AboutMeMessageStore"%>

<%
	// Creats a list of all   to all aboutmemessages
	List<AboutMeMessage> aboutmemessages = (List<AboutMeMessage>) request.getAttribute("aboutmemessage");
%>

<!DOCTYPE html>
<html>
<head>
<title>Profile</title>
<link rel="stylesheet" href="/css/main.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
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
	<!--  Checks if the user is not logged in tells them to log in    -->

	  <nav>
    <a id="navTitle" href="/">CodeU Chat App</a>
    <a href="/conversations">Conversations</a>
    <% if(request.getSession().getAttribute("user") != null){ %>
      <a  href="/userprofile/<%= request.getSession().getAttribute("user")%>">Hello <%= request.getSession().getAttribute("user")%></a>
      <a href="/mentions">Mentions</a>
    <% } else{ %>
      <a href="/login">Login</a>
      <a href="/userprofile/">AboutMe</a>
    <% } %>
    <a href="/about.jsp">About</a>
    <% if(request.getSession().getAttribute("user") != null){ %>
      <a href="/requests">Requests</a>
      <a href="/logout">Logout</a>
    <% } %>    
  </nav>

	
	<!--  Checks if the user is not logged in tells them to log in    -->
	<%
    if ((request.getSession().getAttribute("user") == null)){ 
        %>
	<h3>
		You must <a href="/login">Login</a> to view User Profiles .
	</h3>

	<!--  Checks if the user is logged  and Views someone's else page   -->
	<%
		} else if (request.getSession().getAttribute("user") != null
		  && (!request.getSession().getAttribute("user").equals(request.getAttribute("user")))) {
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
		</strong>  <%=aboutmemessage.getContent()%></li>
		<%
			} %>
		<%
			} 
		%>
	</ul>
	<!--  If the user is on their page they can add and delete information about themselves   -->


	<%
		}else if ((request.getSession().getAttribute("user") != null)
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
		</strong> <%=aboutmemessage.getContent()%>	<form action="<%=userUrl%>" method="POST">
				<button   class="deleteButton" type="submit">Delete</button>
				<input type="hidden" name="deleteAboutme" value="true"> <input
					type="hidden" name="aboutmemessageId"
					value="<%= aboutmemessage.getId() %>">
			</form> <% 
		} %> <% 
		}
		%>
	</ul>

	<form action=" <%=userUrl%>" method="POST">
		<input type="text" name="aboutme"> <br /> <br /> <br />
		<button type="submit">Submit</button>
	</form>



	<%
			} 
		%>



</body>
</html>

