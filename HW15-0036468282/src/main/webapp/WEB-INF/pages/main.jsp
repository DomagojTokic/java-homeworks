<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<body>
	<c:choose>
		<c:when test="${loggedIn}">
			<p>
				<a href="${pageContext.request.contextPath}/servleti/author/${nick}">${nick}</a><br>
				<a href="${pageContext.request.contextPath}/servleti/logout">Logout</a>
			</p>
		</c:when>
		<c:otherwise>
        Anonymous user
        <br>
			<br>
			<form action="login" method="POST">
				Username:<br> <input type="text" name="username"><br> Password:<br> <input type="password"
					name="password"><br> <input type="submit" value="Submit">
			</form>
			<c:if test="${error != null}">
    			${error}   
			</c:if>
			<p>
				<a href="register">Not registered?</a>
			</p>
		</c:otherwise>
	</c:choose>

	<ul>
		<c:forEach var="a" items="${authors}">
			<li><a href="author/${a.nick}">${a.nick}</a></li>
		</c:forEach>
	</ul>
</body>
</html>