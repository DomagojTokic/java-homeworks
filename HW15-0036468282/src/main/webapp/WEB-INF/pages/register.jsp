<%@page import="hr.fer.zemris.java.hw15.forms.BlogUserForm"%>
<%@ page import="java.util.Map"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>

<%
	BlogUserForm form = (BlogUserForm) request.getAttribute("form");
	Map<String, String> errorMsg = form.getValidity();
%>

<html>
<body>
<p>
	Anonymous user<br>
	<a href="${pageContext.request.contextPath}/servleti/main">Login</a>
</p>

<form action="register" method="POST">
	First Name:<br>
	<input type="text" name="firstName" value=<%=form.getFirstName()%>>
	<%=errorMsg.get("firstName")%><br>
	Last Name:<br>
	<input type="text" name="lastName" value=<%=form.getLastName()%>>
	<%=errorMsg.get("lastName")%><br>
	email:<br>
	<input type="text" name="email" value=<%=form.getEmail()%>>
	<%=errorMsg.get("email")%><br>
	Nick:<br>
	<input type="text" name="nick" value=<%=form.getNick()%>>
	<%=errorMsg.get("nick")%><br>
	Password:<br>
	<input type="password" name="password">
	<%=errorMsg.get("password")%><br>
	<input type="submit" value="Register">
</form>
<p><a href="${pageContext.request.contextPath}/servleti/main">Back to main page</a></p>
</body>
</html>