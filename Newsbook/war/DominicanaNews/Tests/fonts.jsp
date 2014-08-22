<!DOCTYPE html>

<html>
<head>
	<meta charset="utf-8">
	<title>Fonts Test</title>
	<link rel="stylesheet" type="text/css" href="fonts.css">
	<!--when adding a family name use + instead of spaces and use | to add multiple fonts
	 To request other styles or weights, append a colon (:) to the name of the font, 
	 followed by a list of styles or weights separated by commas (,). -->
    <link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Tangerine">
    <link href='http://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet' type='text/css'>

</head>
<body>
	<div id='slide'>
		<h2 class='slideTitle'>
		Realizan dia de oracion y tregua en la Franja de Gaza
		</h2>
		<p class='slideText' style="color: #f00;">
    		Los habitantes de la Franja de Gaza llevaron a cabo por 
    	primera vez desde el inicio de la guerra la tradicional 
    	oracion del viernes sin 
		</p>
    	<p class='slideText'>
    	Los habitantes de la Franja de Gaza llevaron a cabo por 
    	primera vez desde el inicio de la guerra la tradicional 
    	oracion del viernes sin 
		</p>
	<div>
    <script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
 	<script src="../clampjs/clamp.js"></script>
 	<script>
 	 //We use clamp.js to clamp both the title and the text
 	 //Clamp title to 2 Lines
 	var numSlides = document.getElementsByClassName('slideText').length;
 	var i;
 	for(i=0; i < numSlides; i++){
 	 //var titleElem = document.getElementsByClassName('slideTitle')[i];
 	 var textElem = document.getElementsByClassName('slideText')[i];
     $clamp(textElem, {clamp: 2, useNativeClamp:true, animate: true});
 	}
 	</script>
</body>
</html>