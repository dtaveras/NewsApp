<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Vector" %>
<%@ page import="newsbook.parsers.NewsObject" %>
<%@ page import="java.util.LinkedList" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
	<title>Testing</title>
    <script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
</head>

<h2><%= request.getAttribute("title") %></h2>
<%
 LinkedList<NewsObject> newsList = (LinkedList<NewsObject>)request.getAttribute("NewsList");
%>
<body>

<%
int numStories = 0;

if(newsList != null){
	numStories = newsList.size();
}

for(int newsListIndex=0; newsListIndex< numStories; newsListIndex++){
	NewsObject newsobj = newsList.get(newsListIndex);
%>
	<h3 style="color: #A00">OBJNUM: <%= newsListIndex%></h3>
	<div class="newsobjclass" style="border-style:solid; border-color: #00f; width: 90%;">
		<span class="objprevtitle" style="color: #0A0">PreviewTitle:</span> <%= newsobj.getPreviewTitle() %><br>
		<!-- Full Title is hidden click preview title to show -->
		<span class="objfulltitle" style="display: none;"><span style="color: #0A0">FullTitle:</span> 
		<span><%= newsobj.getFullTitle()%></span><br>
		</span>
		
		<span class="objprevtext" style="color: #0A0">PreviewText:</span> <%= newsobj.getPreviewText() %>
		<span><%= newsobj.getPreviewText().length() %> </span><br>
		<!-- Full Text is hidden just click preview text to show -->
		<span class="objfulltext" style="display: none;"><span style="color: #0A0">FullText:</span>
		<span><%= newsobj.getFullText()%></span><br>
		</span>
		
		<span style="color: #0A0">Place:</span> <%= newsobj.getPlaceOfOccurence() %><br>
		<span style="color: #0A0">Topic:</span> <%= newsobj.getTopicOrSection() %><br>
		<span style="color: #0A0">Author:</span> <%= newsobj.getAuthor() %><br>
		<span style="color: #0A0">Date:</span> <%= newsobj.getDate() %><br>
		<span style="color: #0A0">SourceUrl:</span> <%= newsobj.getSourceUrl() %><br>
		<span style="color: #0A0">ImageUrl:</span> <%= newsobj.getImageUrl() %>
	</div>
<%}%>

<% if(newsList != null){%>
<script>
	$('.objprevtitle').bind('click', function() {
		var textTitle = document.getElementsByClassName('objprevtitle');
		var textElements = document.getElementsByClassName('objfulltitle');
		var objIndex = $(this).closest('.newsobjclass').index('.newsobjclass');
		console.log(objIndex);
		
		var titleobj = textTitle[objIndex];
		titleobj.style.color = '#A00';
		var textobj = textElements[objIndex];
		textobj.style.display = 'inline';
	});
	
	$('.objfulltitle').bind('click', function() {
		var textTitle = document.getElementsByClassName('objprevtitle');
		var textElements = document.getElementsByClassName('objfulltitle');
		var objIndex = $(this).closest('.newsobjclass').index('.newsobjclass');
		console.log(objIndex);
		
		var titleobj = textTitle[objIndex];
		titleobj.style.color = '#0A0';
		var textobj = textElements[objIndex];
		textobj.style.display = 'none';
	});
	
	$('.objprevtext').bind('click', function() {
		var textTitle = document.getElementsByClassName('objprevtext');
		var textElements = document.getElementsByClassName('objfulltext');
		var objIndex = $(this).closest('.newsobjclass').index('.newsobjclass');
		console.log(objIndex);
		
		var titleobj = textTitle[objIndex];
		titleobj.style.color = '#A00';
		var textobj = textElements[objIndex];
		textobj.style.display = 'inline';
	});
	
	$('.objfulltext').bind('click', function() {
		var textTitle = document.getElementsByClassName('objprevtext');
		var textElements = document.getElementsByClassName('objfulltext');
		var objIndex = $(this).closest('.newsobjclass').index('.newsobjclass');
		console.log(objIndex);
		
		var titleobj = textTitle[objIndex];
		titleobj.style.color = '#0A0';
		var textobj = textElements[objIndex];
		textobj.style.display = 'none';
	});
</script>
<%}%>

</body>
</html>