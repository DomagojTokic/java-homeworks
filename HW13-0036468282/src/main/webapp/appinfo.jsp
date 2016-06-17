<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>

<%
	// number constants represent number of milliseconds in divided entity
	long duration = System.currentTimeMillis() - (long) application.getAttribute("startTime");
	String days = Long.toString(duration/86400000);
	duration = duration%86400000;
	String hours = Long.toString(duration/3600000);
	duration = duration%3600000;
	String minutes = Long.toString(duration/60000);
	duration = duration%60000;
	String seconds = Long.toString(duration/1000);
	String millis = Long.toString(duration%1000);
%>

<html>
<body bgcolor="${pickedBgCol}">
This application has been running for:<br>
<%= days%> days, <%= hours%> hours, <%= minutes%> minutes, <%= seconds%> seconds and <%= millis%> milliseconds.
</body>
</html>