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
	<div class="card">
		<div class="card-body">
			<h4 class="card-title"><spring:message code="domain.campanha" /></h4>
			<form>
				<dwf:setEntity entityName="campanha" entity="${entity}"/>
				<dwf:outputText property="nome" row="true"/>
				<dwf:outputText property="inicio" row="true"/>
				<dwf:outputText property="orcamento" row="true"/>
				
				<a href="/${entityName}/" class="btn btn-secondary"><spring:message code="action.cancel"/></a>
				<button type="submit" class="btn btn-primary" formaction="/${entityName}/${entity.id}/edit" formmethod="get"><spring:message code="action.edit"/></button>
			</form>
		</div>
	</div>
</body>
</html>