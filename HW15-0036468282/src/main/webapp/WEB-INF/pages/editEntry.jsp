<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<body>
	<p>
		<c:choose>
			<c:when test="${loggedIn}">
				<p>
					<a href="${pageContext.request.contextPath}/servleti/author/${nick}">${nick}</a><br> <a
						href="${pageContext.request.contextPath}/servleti/logout">Logout</a>
				</p>
			</c:when>
			<c:otherwise>
        Anonymous user<br>
				<a href="${pageContext.request.contextPath}/servleti/main">Login</a>
			</c:otherwise>
		</c:choose>
	</p>
	<br>
	<br>

	<form action="${pageContext.request.contextPath}/servleti/edit_entry" id="entry" method="POST">
		Title: <input type="text" name="title" width="100" value="${entry.title}"><br> <br>
		<textarea rows="50" cols="150" name="text" form="entry">${entry.text}</textarea>
		<br> <input type="hidden" name="id" value="${entry.id}"> <input type="submit" value="Save">
	</form>
	<br>
<p><a href="${pageContext.request.contextPath}/servleti/main">Back to main page</a></p>
</body>
</html>