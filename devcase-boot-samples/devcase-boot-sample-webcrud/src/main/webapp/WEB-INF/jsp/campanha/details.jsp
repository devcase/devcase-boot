<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title><spring:message code="domain.campanha.plural" /> Form</title>
</head>
<body>
	<h1>
		<spring:message code="domain.campanha.plural" />
	</h1>
	<form:form action="/campanha/${entity.id}/edit" method="GET" commandName="entity">
		<label><spring:message code="campanha.name"/>: </label>
		${entity.name}<br/>
		
		<a href="/campanha">Voltar à lista</a>
		<form:button >Editar</form:button>
	</form:form>
</body>
</html>