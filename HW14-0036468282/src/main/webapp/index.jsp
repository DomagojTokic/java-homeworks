<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="hr.fer.zemris.java.hw14.dao.DAOProvider"%>
<%@page import="java.util.List"%>
<%@page import="hr.fer.zemris.java.hw14.model.Poll"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	List<Poll> polls = DAOProvider.getDao().getPolls();
	request.setAttribute("polls", polls);
%>

<html>
<body>
	<c:forEach var="poll" items="${polls}">
		<p>
			<a href="servleti/glasanje?pollID=${poll.id}">${poll.title}</a>
		</p>
	</c:forEach>
</body>
</html>
