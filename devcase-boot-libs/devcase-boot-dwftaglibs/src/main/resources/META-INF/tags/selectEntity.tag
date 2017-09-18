<%@tag import="org.springframework.data.repository.CrudRepository"%>
<%@tag import="java.util.List"%>
<%@tag import="org.springframework.web.context.WebApplicationContext"%>
<%@tag import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@tag import="org.springframework.data.repository.Repository"%>
<%@tag import="java.util.Map"%>
<%@ tag trimDirectiveWhitespaces="true" dynamic-attributes="attrMap" description="Select with the entities of a defined type"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://boot.devcase.com.br/dwf" prefix="dwf"%>
<%
WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(application);
Map attrMap = (Map) getJspContext().getAttribute("attrMap");
String targetEntityName = (String) attrMap.get("targetEntity");
if (targetEntityName == null)
	targetEntityName = (String) attrMap.get("property");

//TODO - better way to find the repository
CrudRepository dao = (CrudRepository) applicationContext.getBean(targetEntityName + "Repository");

Iterable targetEntityList = dao.findAll();
getJspContext().setAttribute("targetEntityList", targetEntityList);
%>
<dwf:formGroup parentAttrMap="${attrMap}">
	<select class="form-control" name="${name}.id">
		<option value=""></option>
		<c:forEach items="${targetEntityList}" var="targetEntity">
			<option value="${targetEntity.id}" ${targetEntity.id eq value.id ? 'selected' : ''}>${targetEntity}</option>
		</c:forEach>
	</select>
</dwf:formGroup>
