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
<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.Statistic" %>
<%@ page import="codeu.model.data.Statistic.Type" %>
<%@ page import="codeu.model.store.persistence.PersistentStorageAgent" %>

<!DOCTYPE html>
<html>
<head>
  <title>Statistics</title>
  <link rel="stylesheet" href="/css/main.css">
  <!-- Functionality below is adapted from Google Chart's Quickstart guide -->
  <!--Load the AJAX API-->
  <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
  <script type="text/javascript">

    // Load the Visualization API and the corechart package.
    google.charts.load('current', {'packages':['corechart']});

    // Set a callback to run when the Google Visualization API is loaded.
    google.charts.setOnLoadCallback(drawFirstChart);

    google.charts.setOnLoadCallback(drawSecondChart);

    // Callback that creates and populates a data table,
    // instantiates the line chart, passes in the data and draws it
    function drawFirstChart() {

      // Get statistics from Datastore with JSP Java and transform it to JavaScript array
      <% PersistentStorageAgent instance = PersistentStorageAgent.getInstance(); %>
      <% List<Statistic> statistics = instance.loadStatistics(Type.REGISTER_SERVLET_GET_TIME); %>
      var table = new Array();
      <% for (int i = 0; i < statistics.size(); i++) {
           String date = statistics.get(i).getCreationTimeString();
           long value = statistics.get(i).getValue();
      %>
           table[<%= i %>] = ['<%= date %>', <%= value %>];
      <% } %>

      // Create the data table.
      var data = new google.visualization.DataTable();
      data.addColumn('string', 'Creation Time');
      data.addColumn('number', 'Elapsed Time');
      data.addRows(table);

      // Set chart options
      var options = {'title':'Register Servlet GET Elapsed Time',
                     'width':800,
                     'height':600,
                     'backgroundColor':'#f6f6f6',
                     'colors':['green'],
                     'hAxis':{'title':'Creation time'},
                     'vAxis':{'title':'Elapsed time'},
                     'legend':{'position':'none'},
                     'chartArea':{'left':'15%','top':'15%','width':'70%','height':'60%'},
                     'animation':{'startup':true, 'duration':500, 'easing':'in'}
      };

      // Instantiate and draw our chart, passing in some options.
      var chart = new google.visualization.LineChart(document.getElementById('get_chart_div'));
      chart.draw(data, options);
    }

    function drawSecondChart() {

        <% statistics = instance.loadStatistics(Type.REGISTER_SERVLET_POST_TIME); %>
        var table = new Array();
        <% for (int i = 0; i < statistics.size(); i++) {
             String date = statistics.get(i).getCreationTimeString();
             long value = statistics.get(i).getValue();
        %>
             table[<%= i %>] = ['<%= date %>', <%= value %>];
        <% } %>

        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Creation Time');
        data.addColumn('number', 'Elapsed Time');
        data.addRows(table);

        var options = {'title':'Register Servlet POST Elapsed Time',
                       'width':800,
                       'height':600,
                       'backgroundColor':'#f6f6f6',
                       'colors':['green'],
                       'hAxis':{'title':'Creation time'},
                       'vAxis':{'title':'Elapsed time'},
                       'legend':{'position':'none'},
                       'chartArea':{'left':'15%','top':'15%','width':'70%','height':'60%'},
                       'animation':{'startup':true, 'duration':500, 'easing':'in'}
        };

        var chart = new google.visualization.LineChart(document.getElementById('post_chart_div'));
        chart.draw(data, options);
    }

  </script>
</head>

<body>

  <nav>
    <a id="navTitle" href="/">CodeU Chat App</a>
    <a href="/conversations">Conversations</a>
    <% if(request.getSession().getAttribute("user") != null) { %>
      <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
    <% } else { %>
      <a href="/login">Login</a>
    <% } %>
    <a href="/about.jsp">About</a>
  </nav>
  
  <div id="sidenav">
  	<a href="/statistics/chat-servlet">Chat Servlet</a><br>
  	<a href="/statistics/conversation-servlet">Conversation Servlet</a><br>
  	<a href="/statistics/login-servlet">Login Servlet</a><br>
  	<a style="color:#202020;" href="/statistics/register-servlet">Register Servlet</a><br>
  	<a href="/statistics/conversation-store">Conversation Store</a><br>
  	<a href="/statistics/message-store">Message Store</a><br>
  	<a href="/statistics/user-store">User Store</a><br>
  	<a href="/statistics/persistent-data-store">Persistant Data Store</a><br>
  	<a href="/statistics/jsp-pages">JSP Pages</a><br>
  </div>

  <div id="container">
    <h1>Statistics</h1>
    <h2>Register Servlet</h2>
    <!--Div that will hold the line chart-->
    <div id="get_chart_div"></div>
    <br>
    <div id="post_chart_div"></div>
    <br>
  </div>

</body>
</html>
