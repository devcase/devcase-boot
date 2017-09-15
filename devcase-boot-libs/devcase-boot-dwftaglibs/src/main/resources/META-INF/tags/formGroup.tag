<%@tag import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@tag import="org.springframework.web.context.WebApplicationContext"%>
<%@tag import="java.util.Locale"%>
<%@tag import="org.apache.commons.lang3.StringUtils"%>
<%@tag import="org.springframework.context.MessageSource"%>
<%@tag import="org.springframework.web.servlet.tags.RequestContextAwareTag"%>
<%@tag import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%@tag import="org.apache.commons.beanutils.BeanUtilsBean2"%>
<%@tag import="java.util.HashMap"%>
<%@tag import="java.util.Map"%>
<%@ tag trimDirectiveWhitespaces="true" body-content="scriptless" dynamic-attributes="attrMap" %>
<%@ taglib uri="http://boot.devcase.com.br/dwf" prefix="dwf"%>
<%@ variable name-given="name" scope="NESTED" %>
<%@ variable name-given="value" scope="NESTED" variable-class="java.lang.Object"%>
<%@ variable name-given="label" scope="NESTED" variable-class="java.lang.String"%>
<%
WebApplicationContext webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
MessageSource messageSource = webApplicationContext;
Locale locale = RequestContextUtils.getLocale(request);

Map<String, Object> attrMap = (Map<String, Object>) getJspContext().findAttribute("attrMap");
if(attrMap.containsKey("parentAttrMap")) {
	Map<String, Object> parentAttrMap = (Map<String, Object>) attrMap.get("parentAttrMap");
	if(parentAttrMap != null) {
		attrMap = new HashMap<>(attrMap);
		attrMap.putAll(parentAttrMap);
	}
}
Object bean = getJspContext().findAttribute("entity");
String entityName = (String) getJspContext().findAttribute("entityName");
String property = attrMap.containsKey("property") ? (String) attrMap.get("property") : null;


//definir name
String name = "";
if(attrMap.containsKey("name")) {
	name = (String) attrMap.get("name");
} else if (StringUtils.isNotBlank(property)) {
	name = property;
}

//definir label
String label = "";
if(StringUtils.isNotBlank(property)) {
	label = messageSource.getMessage(entityName.concat(".").concat(property), null, locale);
}

//definir value
Object value = "";
if(attrMap.containsKey("value")) {
	value = attrMap.get("value");
} else if (StringUtils.isNotBlank(property)) {
	value = BeanUtilsBean2.getInstance().getPropertyUtils().getProperty(bean, property);
}

getJspContext().setAttribute("name", name);
getJspContext().setAttribute("label", label);
getJspContext().setAttribute("value", value);
%>
<div class="form-group">
	<label for="${name}">${label}</label>
	<jsp:doBody/>
</div>