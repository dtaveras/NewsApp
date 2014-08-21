<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.TimeZone" %>
<%@ page import="java.text.SimpleDateFormat" %>

<header id="banner">
		<div id="banner-inner-wrapper">
			<div id="banner-inner">
	  
				<nav id="menu-nav">
				</nav>
	
				<div id="title">
					<div>
						Dominicanos Al Dia
					</div>
				</div>
				
			</div>
		</div>
		
		<!-- No Infobar for now still experimenting -->
		<!--
        <div id="infobar">
        <% 
        	//Days of the week go from 1-7 and months of the year go from 0-11
      		String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
      				          "Julio", "Agosto", "Septiembre", "Noviembre", "Octubre", "Diciembre"};
      		String[] dias = {"Domingo","Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"};
      		
      		SimpleDateFormat fm = new SimpleDateFormat("dd yyyy");
      		SimpleDateFormat fm_english = new SimpleDateFormat("EEEE MMMM dd yyyy");
      		TimeZone drTZ = TimeZone.getTimeZone("America/Santo_Domingo");
      		Calendar today = new java.util.GregorianCalendar(drTZ);
      		
      		Date date = today.getTime();
       		String dateString = fm.format(date).toString();
       		String dateStringEnglish = fm_english.format(date).toString();
       		
       		String day = dias[today.get(Calendar.DAY_OF_WEEK)-1];
       		String mes = meses[today.get(Calendar.MONTH)];
       		dateString = day + ", " + mes + " " + dateString;
       		request.setAttribute("dateString", dateString);
        %>
        <div id="infoDate">
        	<span>${fn:escapeXml(dateString)}</span>
        </div>
        <div id="infoEscribir">
        	<span><a href='#'>Escribir</a></span>
        </div>
        <div id="infoContact">
        	<span><a href='#'>Contactar</a></span>
        </div> -->
        
        </div>
</header>