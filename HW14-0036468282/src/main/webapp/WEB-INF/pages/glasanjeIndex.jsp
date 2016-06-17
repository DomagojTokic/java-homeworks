<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<body>
	<h1>${poll.title}</h1>
	<p>${poll.message}</p>
	<ol>
		<c:forEach var="e" items="${options}">
			<li> <a href="glasanje-glasaj?pollID=${pollID}&id=${e.id}">${e.optionTitle}</a></li>
		</c:forEach>

	</ol>
</body>
</html>