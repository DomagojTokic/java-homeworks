<%@page import="hr.fer.zemris.java.hw15.forms.BlogEntryForm"%>
<%@ page import="java.util.Map"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	BlogEntryForm form = (BlogEntryForm) request.getAttribute("form");
	Map<String, String> errorMsg = form.getValidity();
%>

<html>
<body>
	<c:choose>
		<c:when test="${loggedIn}">
			<a href="${pageContext.request.contextPath}/servleti/author/${nick}">${nick}</a><br>
			<a href="${pageContext.request.contextPath}/servleti/logout">Logout</a>
		</c:when>
		<c:otherwise>
        Anonymous user<br>
        <a href="${pageContext.request.contextPath}/servleti/main">Login</a>
		</c:otherwise>
	</c:choose>
	<br>
	<br>

	<form action="${pageContext.request.contextPath}/servleti/new_entry" id="entry" method="POST">
		Title: <input type="text" name="title" width="100"><%=errorMsg.get("title")%><br><br>
		<textarea rows="50" cols="150" name="text" form="entry"></textarea><%=errorMsg.get("text")%><br>
		<input type="submit" value="Save">
	</form>
	<br>
<p><a href="${pageContext.request.contextPath}/servleti/main">Back to main page</a></p>
</body>
</html>