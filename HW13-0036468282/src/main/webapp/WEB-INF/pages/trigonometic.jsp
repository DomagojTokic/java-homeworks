<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<body bgcolor="${pickedBgCol}">
	<table>
		<tr>
			<td>Degree</td>
			<td>sin</td>
			<td>cos</td>
		</tr>
		<c:forEach var="e" items="${results}">
			<tr>
				<td>${e.degree}</td>
				<td>${e.sin}</td>
				<td>${e.cos}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>