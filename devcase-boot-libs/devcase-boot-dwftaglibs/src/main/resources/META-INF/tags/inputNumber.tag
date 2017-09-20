<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://boot.devcase.com.br/dwf" prefix="dwf"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ tag dynamic-attributes="attrMap"%>

<dwf:formGroup parentAttrMap="${attrMap}">
	<input type="number" value="${value}" name="${name}"
		<c:if test="${!empty attrMap.step}">step="${attrMap.step}"</c:if>
		<c:if test="${!empty attrMap.pattern}">pattern="${attrMap.pattern}"</c:if>
		<c:if test="${attrMap.required}">required="required"</c:if>
		<c:if test="${!empty attrMap.maxlength}">maxlength="${attrMap.maxlength}"</c:if>
		<c:if test="${!empty attrMap.minlength}">minlength="${attrMap.minlength}"</c:if>
		<c:if test="${!empty attrMap.min}">min="${attrMap.min}"</c:if>
		<c:if test="${!empty attrMap.max}">max="${attrMap.max}"</c:if>
		
		class="form-control ${!empty fieldErrors ? 'is-invalid' : ''} ${attrMap.inputStyleClass}"
	/>
</dwf:formGroup>