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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<body>
<%
  String reqName = (String) request.getAttribute("userName");
  request.setAttribute("nombre", reqName);
  Vector<String> lotery = (Vector<String>)request.getAttribute("losNumeros");
  String num1 = lotery.elementAt(0);
  String num2 = lotery.elementAt(1);
  String num3 = lotery.elementAt(2);
  request.setAttribute("num1", num1);
  request.setAttribute("num2", num2);
  request.setAttribute("num3", num3);
%>
<p> This is what my name should be: ${fn:escapeXml(userName)} </p>
Number 1: ${fn:escapeXml(num1)} <br>
Number 2: ${fn:escapeXml(num2)} <br>
Number 3: ${fn:escapeXml(num3)} <br>
${fn:escapeXml(news)}
<%
	String opt = (String) request.getAttribute("option");
	if(opt.equals("display")){
%>
<h2>Display me!!</h2>
<%}%>
<%

%>
</body>
</html>