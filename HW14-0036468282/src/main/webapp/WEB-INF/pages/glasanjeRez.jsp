<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<style type="text/css">
table.rez td {
	text-align: center;
}
</style>
</head>
<body>
	<h1>${poll.title}</h1>
	<p>${poll.message}</p>
	<table border="1" class="rez">
		<thead>
			<tr>
				<th>Broj glasova</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="e" items="${results}">
				<tr>
					<td>${e.optionTitle}</td>
					<td>${e.votesCount}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<h2>Grafički prikaz rezultata</h2>
	<img alt="Pie-chart" src="glasanje-grafika?pollID=${pollID}" />
	<h2>Rezultati u XLS formatu</h2>
	<p>
		Rezultati u XLS formatu dostupni su <a href="glasanje-xls?pollID=${pollID}">ovdje</a>
	</p>
	<h2>Razno</h2>
	<p>Poveznice za pobjednike:</p>
	<ul>
		<c:forEach var="e" items="${outputSongs}">
			<li><a href="${e.optionLink}" target="_blank">${e.optionTitle}</a></li>
		</c:forEach>
	</ul>
</body>
</html>