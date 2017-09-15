<%@ tag trimDirectiveWhitespaces="true" dynamic-attributes="attrMap"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://boot.devcase.com.br/dwf" prefix="dwf"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<dwf:formGroup parentAttrMap="${attrMap}">
	<fmt:formatDate value="${value}" pattern="yyyy-MM-dd" var="valueOutput"/>
	<input type="date" 
		value="${valueOutput}" name="${name}" <c:if test="${attrMap.required}">required="required"</c:if>
		class='form-control <c:if test="${attrMap.required}">required</c:if>'
	/>
</dwf:formGroup>
