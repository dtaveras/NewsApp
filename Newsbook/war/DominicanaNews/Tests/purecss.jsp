<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
	<meta charset="utf-8">
	<title>New Slide Show</title>
	<meta name="viewport" content="width=device-width,initial-scale=1">
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
	<script type="text/javascript">
	$(window).load(function(){
		//Swap the slides
		var cur_ind = 0;
		var num_slides = $('.slide').length;
		$('#button').bind('click', function(){

			var prv_ind = cur_ind;
			if(cur_ind < num_slides-1){
				cur_ind+= 1;
			}
			else{
				cur_ind = 0;
			}

			//Set the old to off and set the new to on
			$('.slide')[prv_ind].style.display = 'none';
			$('.slide')[cur_ind].style.display = 'inline-block';
		});
	});
	</script>
</head>
<body>
	<div>
		<div class="slide" style="border-style:solid; border-color:#00f;">
			<img src="../Images/slide1.jpg" style="width:100%;">
	        <h2 class="slideTitle">Lorem ipsum dolor sit amet, 
	                     consectetur adipisicing
	        </h2>
	        <div>
	            <p> Lorem ipsum dolor sit amet, 
	                     consectetur adipisicing elit, sed do eiusmod tempor 
	                     incididunt ut labore et dolore magna aliqua.
	            </p>
	        </div><!--/slideText-->
	    </div>
		<div class="slide" style="display:none; border-style:solid; border-color:#0f0;">
			<img src="../Images/slide2.jpg" style="width:100%;">
	        <h2 class="slideTitle">Though memories change I got name tatted on my arm
	        </h2>
	        <div>
	            <p> Lorem ipsum dolor sit amet, 
	                     consectetur adipisicing elit, sed do eiusmod tempor 
	                     incididunt ut labore et dolore magna aliqua.
	            </p>
	        </div><!--/slideText-->
	    </div>
	</div>
	<div id="button">Next</div>
</body>
</html>