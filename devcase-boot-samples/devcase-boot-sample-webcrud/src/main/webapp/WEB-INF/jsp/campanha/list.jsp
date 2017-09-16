<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://boot.devcase.com.br/dwf" prefix="dwf"%>
<html>
<head>
	<title><spring:message code="domain.campanha.plural"/></title>
</head>
<body>
	<h1><spring:message code="domain.campanha.plural"/></h1>
	<a href="/campanha/create">Criar</a>
	<ul>
		<c:forEach items="${list}" var="campanha">
			<li><a href="/campanha/${campanha.id}">${campanha.nome}</a></li>
		</c:forEach>
	</ul>
</body>
</html>