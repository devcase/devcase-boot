<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://boot.devcase.com.br/dwf" prefix="dwf"%>

<!DOCTYPE html>
<html>
<head>
<title><spring:message code="domain.campanha.plural" /> Form</title>
<meta name="decorator" content="/WEB-INF/jsp/decorators/default.jsp"></meta>
</head>
<body>

	<div class="card">
		<div class="card-body">
			<h4 class="card-title"><spring:message code="domain.campanha" /></h4>
			<h5 class="card-subtitle mb-4 text-muted"><spring:message code="action.create" /></h5>
			<form action="/campanha/${entity.id}" method="post">
				<dwf:setEntity entityName="campanha" entity="${entity}"/>
				<dwf:inputText property="nome" required="true"/>
				<dwf:inputDate property="inicio" required="true"/>
				<dwf:inputMoney property="orcamento"/>
				
				<a href="/${entityName}/" class="btn btn-secondary"><spring:message code="action.cancel"/></a>
				<button type="submit" class="btn btn-primary"><spring:message code="action.save"/></button>
			</form>
		</div>
	</div>
</body>
</html>