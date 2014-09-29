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
<head>
	<meta charset="utf-8">
	<title>Dominicanos Al Dia</title>
	<meta name="viewport" content="width=device-width,initial-scale=1">
	<link rel="stylesheet" type="text/css" href="DominicanaNews/MainPage.css">
	<link href='http://fonts.googleapis.com/css?family=Raleway:400' rel='stylesheet' type='text/css'>
	<link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
	
	<!--
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script type="text/javascript" language="javascript" src="DominicanaNews/dotjs/jquery.dotdotdot.min.js"></script>
	
	<script src="http://192.168.2.10:8080/target/target-script-min.js#delvisbugging"></script>
	<link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.5.0/pure-min.css">
	-->
	
	<!--<script>
	function setUpNavigation(){
		var slides_obj = document.getElementsByClassName('slide');
		var num_slides = slides_obj.length;
		var cur_ind = 0;
		var curmarkColor = '#3498DB';
		var	markColor = '#BBB';

		document.getElementsByClassName('navButtons')[0].onclick = managePosition;
		document.getElementsByClassName('navButtons')[1].onclick = managePosition;

		document.getElementsByClassName('slidemark')[cur_ind].style.borderColor = curmarkColor;
		$('#sld0').dotdotdot();
		$('#titl0').dotdotdot();
		
		manageControlBar();
		function managePosition(){
			//Check if we are at the start or end
			var butAtrb = this.getAttribute('id');
			if(butAtrb == 'prvButton' && cur_ind == 0){
				return;
			}
			else if(butAtrb == 'nxtButton' && cur_ind == num_slides-1){
				return;
			}
			var prv_ind = cur_ind;
			cur_ind = (butAtrb == 'prvButton') ? cur_ind-1 : cur_ind+1;

			var slideMarkCur = document.getElementsByClassName('slidemark')[cur_ind];
			var slideMarkPrv = document.getElementsByClassName('slidemark')[prv_ind];
			var slideCur = document.getElementsByClassName('slide')[cur_ind];
			var slidePrv = document.getElementsByClassName('slide')[prv_ind];
			
			//update pagemark colors
			slideMarkCur.style.borderColor = curmarkColor;
			slideMarkPrv.style.borderColor = markColor;
			
			//update display
			slidePrv.style.display = 'none';
			slideCur.style.display = 'inline-block';
			
			if(cur_ind == 0){
				$('#sld0').dotdotdot();
				$('#titl0').dotdotdot();
			}
			else if(cur_ind == 1){
				$('#sld1').dotdotdot();
				$('#titl1').dotdotdot();
			}
			else if(cur_ind == 2){
				$('#sld2').dotdotdot();
				$('#titl2').dotdotdot();
			}
			
			manageControlBar();
		};
		
		function manageControlBar(){
			if(cur_ind == 0){
				//document.getElementById('prvButton').style.color = '#00f';
			}
			else{
				//document.getElementById('prvButton').style.color = '#fff';				
			}

			if(cur_ind == num_slides-1){
				//document.getElementById('nxtButton').style.color = '#0f0';
			}
			else{
				//document.getElementById('nxtButton').style.color = '#fff';
			}

		};

	};
	
	window.onload = setUpNavigation;
	</script>-->
</head>
<body>
	<div id="page-container">
		<jsp:include page="Banner.jsp"/>
		<jsp:include page="TopNews.jsp"/>
		<%--<jsp:include page="slideShow2.jsp"/> --%>
		<%-- <jsp:include page="loteriaTable.jsp"/> --%>
		<br>
		<div style="height:100px;">
		</div>
	</div>
</body>
</html>