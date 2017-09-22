<%@ tag trimDirectiveWhitespaces="true" dynamic-attributes="attrMap"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://boot.devcase.com.br/dwf" prefix="dwf"%>
<dwf:formGroup parentAttrMap="${attrMap}">
	<c:if test="${!empty value}">
		<input type="text" readonly class="form-control-plaintext" 
		value="<dwf:autoFormat value='${value}'/>">
	</c:if>
	<c:if test="${empty value}">
		<span class="form-control-plaintext text-muted">- <spring:message code="label.empty"/> -</span>
	</c:if>
</dwf:formGroup>