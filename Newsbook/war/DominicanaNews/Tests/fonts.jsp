<!DOCTYPE html>

<html>
<head>
	<meta charset="utf-8">
	<title>Fonts Test</title>
	<link rel="stylesheet" type="text/css" href="fonts.css">
	<!--when adding a family name use + instead of spaces and use | to add multiple fonts
	 To request other styles or weights, append a colon (:) to the name of the font, 
	 followed by a list of styles or weights separated by commas (,). -->
    <!--<link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Tangerine">
    
    <link href='http://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet' type='text/css'>-->
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script type="text/javascript" language="javascript" src="../dotjs/jquery.dotdotdot.min.js"></script>
	<script>
		function setup(){
			console.log("setup");
			$('#example2').dotdotdot();
		};
		window.onload = setup;
	</script>
</head>
<body>
	<div id='slide'>
		<h2 class='slideTitle'>
		Realizan dia de oracion y tregua en la Franja de Gaza
		</h2>
		<!--<p class='slideText' style="color: #f00;">
    		Los habitantes de la Franja de Gaza llevaron a cabo por 
    	primera vez desde el inicio de la guerra la tradicional 
    	oracion del viernes sin 
		</p>
    	<p class='slideText'>
    	Los habitantes de la Franja de Gaza llevaron a cabo por 
    	primera vez desde el inicio de la guerra la tradicional 
    	oracion del viernes sin 
		</p>-->
    	<p class="example" style="border-style:solid;">
    	Realizan dia de oracion y tregua en la Franja de Gaza
    	Los habitantes de la Franja de Gaza llevaron a cabo por 
    	primera vez desde el inicio de la guerra la tradicional 
    	oracion del viernes sin Los habitantes de la Franja de Gaza 
    	llevaron a cabo por primera vez desde el inicio de la guerra 
    	la tradicional oracion del viernes sin
    	</p>
    	<div class="slideText"><p id="example2" style="border-style:solid; border-color:#0f0; height:60px">
    	Realizan dia de oracion y tregua en la Franja de Gaza
    	Los habitantes de la Franja de Gaza llevaron a cabo por 
    	primera vez desde el inicio de la guerra la tradicional 
    	oracion del viernes sin Los habitantes de la Franja de Gaza 
    	llevaron a cabo por primera vez desde el inicio de la guerra 
    	la tradicional oracion del viernes sin
    	</p></div>
	<div>
 	
</body>
</html>