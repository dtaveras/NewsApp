<html>
	<head>
		<title>Display Data</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width,initial-scale=1">
		<link rel="stylesheet" href="display.css">
		<link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.5.0/pure-min.css">
		<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
		<style>
			html, body, div, span, object, iframe, h1, h2, h3, h4, h5, h6, p, blockquote, pre, abbr, address, cite, code, del, dfn, em, img, ins, kbd, q, samp, small, strong, sub, sup, var, b, i, dl, dt, dd, ol, ul, li, fieldset, form, label, legend, table, caption, tbody, tfoot, thead, tr, th, td, article, aside, canvas, details, figcaption, figure, footer, header, hgroup, menu, nav, section, summary, time, mark, audio, video {
    			margin: 0;
    			padding: 0;
    			color: #000;

			    vertical-align: baseline;
    			background: transparent;
				border-width: 1px;    
			}
			
			.news-article {
				width: 95%;
				margin: 0 auto;
				border-color: #CCC;
				border-radius: 3px;
				border-style: solid;
			}
			.title {
				display: inline-block;
				font-size: 14px;
				color: #fff;
				background-color: #0078e7;
				padding: 4px;
			}
			.text {
				font-size: 12px;
			}
			.section {
				border-color: #CCC;
				border-bottom-style: solid;
				padding: 4px;
			}
			#artnum-wrapper {
				border-color: #CCC;
				border-style: solid;
				border-radius: 3px;
				margin-left: 4px;
				padding: 6px;
			}
			#artnum-title{
				font-size: 18px;
				font-weight: bold;
				color: #00A;
				padding: 5px;
			}
			#artnum {
				font-size: 18px;
				font-weight: bold;
				color: #00A;
				padding-right: 4px;
			}
			a:link{
				color: #AAA;
				text-decoration: underline;
			}
			a:visited{
				color: #AAA; 
				text-decoration: underline;
			}
		</style>
		<script>
			
			function printFormData(){
				var source = fetchOptions.elements["News_Source"].value;
				var section = fetchOptions.elements["Sections"].value;
				var article = getCurArticle(source, section);
				
				console.log(source);
				console.log(section);
				console.log(article);
			}
			
			function getCurArticle(source, section){
				var objFound = null;
			    $.each(formData, function(index, srcObj) {
			    	if(srcObj.value == source){
			    		$.each(formData[index].sections, function(index, object){
			    			if(object.name == section){//found it
			    				objFound = object;
			    			}
			    		});
			    	}
			    });
			    return objFound;
			}
			
			function incrementArticleNum(){
				var source = fetchOptions.elements["News_Source"].value;
				var section = fetchOptions.elements["Sections"].value;
				var article = getCurArticle(source, section);
				article.num = article.num + 1;
				$('#artnum').text(article.num);
			}
			
			$(document).ready(function(){
				$('#submit').click(function(event){
					console.log("Trying to submit");
					var username = "DelvisTaveras";
					var username2 = "JohnDoe";
					
					$.get('/updatedisplay',{user:username, user2:username2},function(responseText) {
						$('#respText').text(responseText);
						console.log(responseText);
                    });
					//the default submit causes the page to refresh which we don't want in
					//this case
					event.preventDefault();
				});
				printFormData();
			});
			
		</script>
	</head>
	<body>
		<div id="page-container">
		<br>
		<span id="artnum-wrapper">   <span id="artnum-title">Article Number: </span>   <span id="artnum">0</span>   </span>
		<br><br>
		<div class="news-article">
			<div class="section">
				<div class="title">PreviewTitle:</div>
				<span class="text">Peligro Castillo piensa traer ...</span>
			</div>

			<div class="section">
				<div class="title">Author:</div>
				<span class="text"></span>
			</div>			

			
			<div class="section">
				<div class="title">Section:</div>
				<span class="text">Educacion</span>
			</div>
						
			<div class="section">
				<div class="title">Date:</div>
				<span class="text">Lunes, 29 Septiembre 2014 </span>
			</div>			
			
			<div class="section">
				<div class="title">Place:</div>
				<span class="text">Santo Domingo</span>
			</div>
			
			<div class="section">
				<div class="title">Source Url:</div>
				<span class="text"><a href=<%="http://diariodom.com/noticias/index.php?id=114272" %>>http://diariodom.com/noticias/index.php?id=114272</a></span>
			</div>
			
			<div class="section">
				<div class="title">Image Url:</div>
				<span class="text">http://diariodom.com/upload/articles/image-114272-123666_full.jpg</span>
			</div>			
			
			<div class="section">
				<div class="title">PreviewText</div>
				<span class="text">.- El ministro de Energia y Minas, Pelegrin Castillo, anuncio este lunes que gestiona con entidades educativas ...</span>
			</div>	
		</div>
		<br>
		<form name="fetchOptions">
			Source: <select name="News_Source"></select>
			Sections: <select name="Sections"></select>
			<input type="submit" id="submit" value="Submit">
		</form>
		<script>
			var formData = [{value:"EldiarioNews",name:"El Diario", sections:[{name:"TopNews",num:-1},{name:"Educacion",num:-1},{name:"Nacionales",num:-1}] },
			                {value:"blogspot",name:"Blogspot", sections:[{name:"Sect1",num:-1},{name:"Sect2",num:-1}]}
			                ];
			var selectedNewsValue = formData[0].value;
			var selectedSectIndex = 0
			
			function render() {
			    // render the first combo
			    $('select[name=News_Source]').empty();
			    $.each(formData, function(index, object) {
			        var selected = "";
			        if (selectedNewsValue == object.value) {
			            selected = "selected";
			        }
			        //console.log(this);
			        $('select[name=News_Source]').append("<option value='" + object.value + "'   " + selected + ">" + object.name + "</option>");
			    })

			    // render the second combo
			    $('select[name=Sections]').empty();
			    $.each(formData, function(index, object) {
			    	if(selectedNewsValue == object.value){
			    		
			    		//console.log(this);
			    		$.each(formData[index].sections, function(index, object){
			    			 //console.log(object.name);
			    			 $('select[name=Sections]').append("<option value='" + object.name + "'>" + object.name + "</option>");
			    		});
				       
			    	}
			    });
			};
			
			function main(){
			    $("select[name=News_Source]").bind("change", function(event) {
			        selectedNewsValue = event.currentTarget.value;
			        render();
			    });
			    render();
			};
			
			main();
		</script>
		<div id="respText"></div>
		<button onclick="printFormData()">
			PrintForm
		</button>
		<button onclick="incrementArticleNum()">
			Increment
		</button>
		<!--
		<script>
			function printFormValues(){
		    	console.log("Source: "+ fetchOptions.elements["News_Source"].value);
		    	console.log("Section: "+ fetchOptions.elements["Sections"].value);
			};
		</script>
		-->
		</div>		
	</body>
</html>