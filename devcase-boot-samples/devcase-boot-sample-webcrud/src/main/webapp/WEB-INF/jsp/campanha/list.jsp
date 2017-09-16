<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://boot.devcase.com.br/dwf" prefix="dwf"%>

<!DOCTYPE html>
<html>
<head>
<title><spring:message code="domain.campanha.plural" /> Details</title>
<meta name="decorator" content="/WEB-INF/jsp/decorators/default.jsp"></meta>
</head>
<body>
	<dwf:setEntity entityName="campanha"/>
	<div class="mb-2">
		<a class="btn btn-primary" href="/${entityName}/create"><spring:message code="action.create"/></a>
	</div>
	<dwf:dataGrid columns="nome,inicio,orcamento"/>
</body>
</html>
