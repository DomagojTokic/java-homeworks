<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="hr.fer.zemris.java.hw15.forms.BlogCommentForm"%>
<%@ page import="java.util.Map"%>

<%
	BlogCommentForm form = (BlogCommentForm) request.getAttribute("commentForm");
	Map<String, String> errorMsg = form.getValidity();
%>

<html>
<body>
	<p>
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
	</p>

	<h1>${entry.title}</h1>
	<table style="width: 60%" border="1">
		<tr>
			<td height="150" style="text-align: left; vertical-align: top; padding: 0">${entry.text}</td>
		</tr>
	</table>
	<br>
	
	<c:choose>
		<c:when test="${editable}">
			<form action="${pageContext.request.contextPath}/servleti/edit_entry" id="entry" method="GET">
				<input type="hidden" name="id" value="${entry.id}"> <input type="submit" value="Edit">
			</form>
		</c:when>
	</c:choose>
	
	<c:forEach var="c" items="${comments}">
		<p>
			<b>${c.postedOn}</b><br> ${c.message}
		</p>
	</c:forEach>
	
	<form action="${pageContext.request.contextPath}/servleti/new_comment" id="comment" method="POST">
		email: <input type="text" name="email" width="100"><%=errorMsg.get("email")%><br> <br>
		<textarea rows="3" cols="120" name="commentText" form="comment"></textarea><%=errorMsg.get("text")%>
		<br> <input type="hidden" name="id" value="${entry.id}"> <input type="submit" value="Comment">
	</form>
	<p><a href="${pageContext.request.contextPath}/servleti/main">Back to main page</a></p>
</body>
</html>