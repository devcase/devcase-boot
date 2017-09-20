<%@tag import="org.springframework.data.repository.CrudRepository"%>
<%@tag import="java.util.Map"%>
<%@tag import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@tag import="org.springframework.web.context.WebApplicationContext"%>
<%@ tag trimDirectiveWhitespaces="true" dynamic-attributes="attrMap" description="Creates a html5 datalist with the existent entities names"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ attribute name="property" type="java.lang.String"%>
<%@ attribute name="listId" required="true" type="java.lang.String"%>
<%@ attribute name="targetEntity" type="java.lang.String"%>
<%--
Usage example:
	<dwf:entityNamesDatalist listId="petslist" property="pet"/>
	<dwf:inputEntityName listId="petslist" property="pet"/>

--%>
<%
	WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(application);
	Map attrMap = (Map) getJspContext().getAttribute("attrMap");
	String targetEntityName = this.targetEntity;
	if (targetEntityName == null)
		targetEntityName = property;

	//TODO - better way to find the repository
	CrudRepository dao = (CrudRepository) applicationContext.getBean(targetEntityName + "Repository");

	Iterable targetEntityList = dao.findAll();
	getJspContext().setAttribute("targetEntityList", targetEntityList);
%>
<datalist id="${listId}">
	<c:forEach items="${targetEntityList}" var="candidate">
		<option value="${candidate.name}" data-id="${candidate.id}"/>
	</c:forEach>
</datalist>