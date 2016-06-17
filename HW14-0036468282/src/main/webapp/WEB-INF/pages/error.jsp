<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>

<%
	String color = (String) session.getAttribute("pickedBgCol");
	if (color == null)
		color = "white";
%>

<html>
<h1>Error: ${message}</h1>
<body bgcolor="<%= color%>">
</body>
</html>