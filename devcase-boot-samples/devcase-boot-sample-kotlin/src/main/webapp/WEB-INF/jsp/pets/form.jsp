<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://boot.devcase.com.br/dwf" prefix="dwf"%>
<dwf:setEntity entityName="pet" entity="${entity}"/>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="domain.${entityName}.plural" /></title>
<meta name="decorator" content="/WEB-INF/jsp/decorators/default.jsp"></meta>
</head>
<body>

	<div class="card">
		<div class="card-body">
			<h4 class="card-title"><spring:message code="domain.${entityName}" /></h4>
			<c:if test="${empty entity.id}"><h5 class="card-subtitle mb-4 text-muted"><spring:message code="action.create" /></h5></c:if>
			<c:if test="${!empty entity.id}"><h5 class="card-subtitle mb-4 text-muted"><spring:message code="action.edit" /></h5></c:if>
			<form action="/${pathPrefix}/${entity.id}" method="post">

				<dwf:inputText property="name" required="true" row="true"/>
				<dwf:inputText property="owner" required="true" row="true"/>
				<dwf:inputText property="email" required="true" row="true"/>
				<dwf:inputNumber property="weight" row="true" step="0.1"/>
				
				<c:if test="${empty entity.id}"><a href="/${pathPrefix}/" class="btn btn-secondary"><spring:message code="action.cancel"/></a></c:if>
				<c:if test="${!empty entity.id}"><a href="/${pathPrefix}/${entity.id}" class="btn btn-secondary"><spring:message code="action.cancel"/></a></c:if>
				<button type="submit" class="btn btn-primary"><spring:message code="action.save"/></button>
			</form>
		</div>
	</div>
</body>
</html>