<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
	<meta charset="utf-8">
	<title>Tests Directory</title>
</head>
<body>
	<h2 style="text-decoration: underline"> Select A Test</h2>
	<form action="/parsingtest" method="GET">
		<select name="TestOption">
		<option value="Education" Selected>Educacion</option>
		<option value="National">Nacional</option>
		<option value="Lottery">Loteria</option>
		</select>
		<input type="submit" value="Submit">
	</form>
</body>
</html>