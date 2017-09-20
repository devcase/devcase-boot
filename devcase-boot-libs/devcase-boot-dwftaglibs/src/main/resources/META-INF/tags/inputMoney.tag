<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://boot.devcase.com.br/dwf" prefix="dwf"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ tag dynamic-attributes="attrMap" trimDirectiveWhitespaces="true" body-content="empty" %>

<dwf:formGroup parentAttrMap="${attrMap}" expectedType="org.javamoney.moneta.Money">
	<!-- output correto para valores de zero centavos -->
	<fmt:formatNumber value="${empty value ? 0 : value.divideToIntegralValue(1).number}" pattern="0" var="integerpart"/>
	<fmt:formatNumber value="${empty value ? 0 : value.remainder(1).multiply(100).number}" pattern="00" var="centspart"/>
	<c:set var="outputValue" value="${integerpart}.${centspart}"/>
	
	<div class="input-group">
		<span class="input-group-addon">$ </span>
		<input type="number" 
			value="${outputValue}"
			name="${name}"
			step="0.01"
			<c:if test="${!empty attrMap.pattern}">pattern="${attrMap.pattern}"</c:if>
			<c:if test="${attrMap.required}">required="required"</c:if>
			<c:if test="${!empty attrMap.maxlength}">maxlength="${attrMap.maxlength}"</c:if>
			<c:if test="${!empty attrMap.minlength}">minlength="${attrMap.minlength}"</c:if>
			<c:if test="${!empty attrMap.min}">min="${attrMap.min}"</c:if>
			<c:if test="${!empty attrMap.max}">max="${attrMap.max}"</c:if>
		class="form-control ${!empty fieldErrors ? 'is-invalid' : ''} ${attrMap.inputStyleClass}"
	/>
	</div>

</dwf:formGroup>