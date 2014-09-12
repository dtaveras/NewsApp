<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Vector" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
	<meta charset="utf-8">
	<title>Loteria Test </title>
</head>
<body>
<h2 style="color:#BB0; ">Lottery Test</h2>
<%
	Vector<String> lns1_num = (Vector<String>)request.getAttribute("lns1_num");
	Vector<String> lns2_num = (Vector<String>)request.getAttribute("lns2_num");
	Vector<String> qp_num = (Vector<String>)request.getAttribute("qp_num");
	Vector<String> pgm_num = (Vector<String>)request.getAttribute("pgm_num");
%>

<div style="border-style: solid; float: left; width:45%;">
	<h3 style="color:#0B0">Loteria National Sorteo 1:</h3>
	Numeros: <%=lns1_num.get(0)%> <%=lns1_num.get(1)%> <%=lns1_num.get(2)%><br>
	Time: <%=request.getAttribute("lns1_time")%> <br>
	Date: <%=request.getAttribute("lns1_date")%>
</div>
<div style="border-style: solid; float: left; width:45%;">
	<h3 style="color:#0B0">Loteria National Sorteo 2:</h3>
	Numeros: <%=lns2_num.get(0)%> <%=lns2_num.get(1)%> <%=lns2_num.get(2)%><br>
	Time: <%=request.getAttribute("lns2_time")%> <br>
	Date: <%=request.getAttribute("lns2_date")%>
</div>
<div style="border-style: solid; float: left; width:45%;">
	<h3 style="color:#0B0">Quiniela Pale:</h3>
	Numeros: <%=qp_num.get(0)%> <%=qp_num.get(1)%> <%=qp_num.get(2)%><br>
	Time: <%=request.getAttribute("qp_time")%> <br>
	Date: <%=request.getAttribute("qp_date")%>
</div>
<div style="border-style: solid; float: left; width: 45%">
	<h3 style="color:#0B0">Pega Mas:</h3>
	Numeros: <%=pgm_num.get(0)%> <%=pgm_num.get(1)%> <%=pgm_num.get(2)%><br>
	Time: <%=request.getAttribute("pgm_time")%> <br>
	Date: <%=request.getAttribute("pgm_date")%>
</div>
</body>
</html>