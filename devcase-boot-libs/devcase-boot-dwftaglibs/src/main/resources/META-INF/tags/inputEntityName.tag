<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://boot.devcase.com.br/dwf" prefix="dwf"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ tag dynamic-attributes="attrMap"%>
<%--
	<datalist id="petslist">
		<option value="Pingo"/>
		<option value="Kuro"/>
	</datalist>
	<dwf:inputEntityName listId="petslist"/>
	
	Para funcionar, o WebBinder precisa estar preparado para receber um nome de entidade e transformar
	em uma instância. Ver br.com.devcase.boot.webcrud.webbinding.NamedEntityPropertyEditor<E>
 --%>
<dwf:formGroup parentAttrMap="${attrMap}">
	<input type="text" value="${value.name}" name="${name}"
		<c:if test="${attrMap.required}">required="required"</c:if>
		<c:if test="${!empty attrMap.listId}">list="${attrMap.listId}"</c:if>
		class="form-control ${!empty fieldErrors ? 'is-invalid' : ''} ${attrMap.inputStyleClass}"
	/>
</dwf:formGroup>