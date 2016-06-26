<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<body>
	<p>
		<c:choose>
			<c:when test="${loggedIn}">
				<a href="${pageContext.request.contextPath}/servleti/author/${nick}">${nick}</a>
				<br>
				<a href="${pageContext.request.contextPath}/servleti/logout">Logout</a>
			</c:when>
			<c:otherwise>
        Anonymous user<br>
				<a href="${pageContext.request.contextPath}/servleti/main">Login</a>
			</c:otherwise>
		</c:choose>
	</p>

	<p>
		<c:choose>
			<c:when test="${entries.isEmpty()}">
      <p>No entries!</p>
			</c:when>
			<c:otherwise>
				<c:forEach var="e" items="${entries}">
					<a href="${pageContext.request.contextPath}/servleti/author/${e.author.nick}/${e.id}">${e.title}</a>
					<br>
				</c:forEach>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${editable}">
				<form action="${pageContext.request.contextPath}/servleti/new_entry" method="GET">
					<input type="submit" value="New">
				</form>
			</c:when>
		</c:choose>
	</p>
<p><a href="${pageContext.request.contextPath}/servleti/main">Back to main page</a></p>
</body>
</html>