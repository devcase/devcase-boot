<%@ tag trimDirectiveWhitespaces="true" body-content="scriptless" dynamic-attributes="attrMap"%>
<%@ taglib uri="http://boot.devcase.com.br/dwf" prefix="dwf"%>
<dwf:mergeMaps map1="${attrMap}" map2="${attrMap.parentAttrMap}" var="attrMap"/>
<div class="form-group">
	<label for="exampleInputEmail1">Email address</label>
	<jsp:doBody/>
</div>