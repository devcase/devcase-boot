<%@ tag trimDirectiveWhitespaces="true" dynamic-attributes="attrMap"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://boot.devcase.com.br/dwf" prefix="dwf"%>
<dwf:formGroup parentAttrMap="${attrMap}">
	<input type="${empty attrMap.type ? 'text' : attrMap.type}" value="<dwf:autoFormat value='${value}'/>" name="${name}"
		<c:if test="${!empty attrMap.pattern}">pattern="${attrMap.pattern}"</c:if> <c:if test="${attrMap.required}">required="required"</c:if>
		<c:if test="${!empty attrMap.placeholder}">placeholder="<spring:message code="${attrMap.placeholder}" text="${attrMap.placeholder}"/>"</c:if>
		<c:if test="${!empty attrMap.maxlength}">maxlength="${attrMap.maxlength}"</c:if>
		<c:if test="${empty attrMap.maxlength}">maxlength="200"</c:if><%-- Padr�o - limite de 200 caracteres --%>
		<c:if test="${!empty attrMap.minlength}">minlength="${attrMap.minlength}"</c:if>
		<c:if test="${!empty attrMap.listId}">list="${attrMap.listId}"</c:if>
		class="form-control ${!empty fieldErrors ? 'is-invalid' : ''} ${attrMap.inputStyleClass}"
	/>
</dwf:formGroup>