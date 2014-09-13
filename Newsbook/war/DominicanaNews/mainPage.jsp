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
	<link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.5.0/pure-min.css">
	<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">

	<link rel="stylesheet" type="text/css" href="DominicanaNews/main.css">
	<link rel="stylesheet" type="text/css" href="DominicanaNews/slideShow.css">
    <script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
    
	<script>
    $( document ).ready(function() {
    	$('h1').css('border-color','#f00');
    });
    </script>
    
    <script>
    $(document).ready(function() {
                   
      var currentPosition = 0;
      var slideWidth = $('.slide').width();
      var slides = $('.slide');
      var numberOfSlides = slides.length;
      var slideShowInterval;
      var speed = 10000;
      var slideShowWidth = $('#slideshow').width();
      
      //printInfo();
      for(var i=0; i< numberOfSlides; i++){
          slides[i].style.width = slideShowWidth;
      }
      //printInfo();
      slideWidth = $('.slide').width();

      /*var containerElem = document.getElementById('page-container');
      var textDiv = document.createElement("DIV");
      var img1 = document.getElementsByTagName('img')[0];
      var slide = document.getElementsByClassName("slide")[0];
      
      textDiv.innerHTML = $('.slide').width();
      document.body.appendChild(textDiv);*/
      
      /*slideShowInterval = setInterval(changePosition, speed);*/
      /*slides.wrapAll('<div id="slidesHolder"></div>');*/
      /*slides.css({ 'float' : 'left' });*/
      
      $('#slidesHolder').css('width', slideWidth * (numberOfSlides+1));
      
      managePosition(currentPosition);
      $('.pageCount').text('1');
      $('.pure-button').bind('click', function() {
        if(currentPosition==0 && $(this).attr('id') == 'prvButton' ){
          return;
        }
        if(currentPosition == numberOfSlides-1 && $(this).attr('id') == 'nxtButton'){
          return;
        }

        currentPosition = ($(this).attr('id')=='nxtButton')
        ? currentPosition+1 : currentPosition-1;
        managePosition(currentPosition);
        moveSlide();
      });

      function printInfo(){
    	  var sw = $('.slide').width();
    	  var ssw = $('#slideshow').width();
    	  var image = $('img').width();
          console.log("Slide Width:", sw);
          console.log("SlideShowWidth:", ssw);
          console.log("ImageWidth:", image)
      }
      
      /* Sets the color of prv and nxt button depending on which slide we are on*/
      function managePosition(position){
        $('.pageCount').text(position+1);
        if(position == 0){
          $('#prvButton').css('opacity', '0.3');
          $('#nxtButton').css('opacity', '1.0');
        }
        else if(position == numberOfSlides-1){
          $('#nxtButton').css('opacity', '0.3');
          $('#prvButton').css('opacity', '1.0');
        }
        else{
           $('#prvButton').css('opacity', '1.0');
           $('#nxtButton').css('opacity', '1.0');
        }
      }
      
      function moveSlide() {
          $('#slidesHolder')
            .animate({'marginLeft' : slideWidth*(-currentPosition)});
      }
    });
  </script>
  
</head>

<body>
	<div id="page-container">
	<jsp:include page="banner.jsp"/>
	<jsp:include page="slideshow.jsp"/>
	<%-- <jsp:include page="loteriaTable.jsp"/> --%>
	<div style="height:300px;">
	</div>
	</div>
	
	<script src="DominicanaNews/clampjs/clamp.js"></script>
	<%--<script>
		var numSlides = document.getElementsByClassName('slide').length;
		var i;
		//Clamp text to 3 lines and title to 2
		for(i=0; i< numSlides; i++){
			var titleElem = document.getElementsByClassName('slideTitle')[i];
			var textElem = document.getElementsByClassName('slideText')[i];
			$clamp(textElem, {clamp: 3, useNativeClamp:false, animate:false});
			$clamp(titleElem, {clamp: 2, useNativeClamp:false, animate:false});
		}
	</script>--%>
</body>

</html>