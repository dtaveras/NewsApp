<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>

<%@ page import="com.google.appengine.api.datastore.Entity"%>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory"%>
<%@ page import="com.google.appengine.api.datastore.DatastoreService"%>
<%@ page import="com.google.appengine.api.datastore.Key"%>
<%@ page import="com.google.appengine.api.datastore.KeyFactory"%>
<%@ page import="com.google.appengine.api.datastore.FetchOptions"%>
<%@ page import="com.google.appengine.api.datastore.Query"%>
<%@ page import="newsbook.storage.NewsDatastorageManager"%>
<%@ page import="newsbook.parsers.DiarioParser"%>

<%@ page import="java.util.List"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.Vector"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
<title>Read Datastore</title>
</head>
<body>
	<%
		Key allNewsKey = KeyFactory.createKey(
				NewsDatastorageManager.const_topkind,
				NewsDatastorageManager.const_topname);
	
		Key diarioKey = KeyFactory.createKey(allNewsKey,
				DiarioParser.const_newsType, DiarioParser.const_name);
		
		//Simple query for a set of articles
		Query allQuery = new Query("News_Article").setAncestor(diarioKey);
				
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
				
		List<Entity> list = datastore.prepare(allQuery).asList(FetchOptions.Builder.withLimit(20));
		out.println("I was able to read " + list.size() + " articles.");
	%>
</body>
</html>