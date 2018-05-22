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

    <% 
    	List<String> admins = Arrays.asList("amejia", "shershey", "Rodrigo", "quinykb", "Israel");
    	String username = request.getSession().getAttribute("user");

    	if(username != null){ 
    %>
        <p>You must <a href="/login">login</a> and be an admin to view this page.</p>

    <% 
		} else if (!admins.contains(username)) { 
	%>
    	<p>Sorry! Only admins can view this page.</p>

    <%  
		} else {
	%>
		<p>Hi <% username %>! Welcome to the admin page!</p>

	<%
		}
	%>
  </div>
</body>
</html>