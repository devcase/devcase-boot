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
<%@ tag trimDirectiveWhitespaces="true" body-content="scriptless" dynamic-attributes="attrMap"%>
<%@ taglib uri="http://boot.devcase.com.br/dwf" prefix="dwf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ variable name-given="name" scope="NESTED"%>
<%@ variable name-given="value" scope="NESTED" variable-class="java.lang.Object"%>
<%@ variable name-given="label" scope="NESTED" variable-class="java.lang.String"%>
<%
	WebApplicationContext webApplicationContext = WebApplicationContextUtils
			.getRequiredWebApplicationContext(request.getServletContext());
	MessageSource messageSource = webApplicationContext;
	Locale locale = RequestContextUtils.getLocale(request);

	Map<String, Object> attrMap = (Map<String, Object>) getJspContext().findAttribute("attrMap");
	if (attrMap.containsKey("parentAttrMap")) {
		Map<String, Object> parentAttrMap = (Map<String, Object>) attrMap.get("parentAttrMap");
		if (parentAttrMap != null) {
			attrMap = new HashMap<>(attrMap);
			attrMap.putAll(parentAttrMap);
			getJspContext().setAttribute("attrMap", attrMap);
		}
	}
	Object bean = getJspContext().findAttribute("entity");
	String entityName = (String) getJspContext().findAttribute("entityName");
	String property = attrMap.containsKey("property") ? (String) attrMap.get("property") : null;

	//definir name
	String name = "";
	if (attrMap.containsKey("name")) {
		name = (String) attrMap.get("name");
	} else if (StringUtils.isNotBlank(property)) {
		name = property;
	}

	//definir value
	Object value = "";
	if (attrMap.containsKey("value")) {
		value = attrMap.get("value");
	} else if (bean != null && StringUtils.isNotBlank(property)) {
		value = BeanUtilsBean2.getInstance().getPropertyUtils().getProperty(bean, property);
	}

	getJspContext().setAttribute("property", property);
	getJspContext().setAttribute("name", name);
	getJspContext().setAttribute("value", value);
%>
<c:choose>
	<c:when test="${attrMap.row eq true}">
		<div class="form-group row">
			<label for="${name}" class="col col-form-label"><dwf:labelTextFor property="${property}"/></label>
			<div class="col-sm-9">
				<jsp:doBody />
			</div>
		</div>
	
	</c:when>
	<c:otherwise>
		<div class="form-group">
			<label for="${name}"><dwf:labelTextFor property="${property}"/></label>
			<jsp:doBody />
		</div>
	</c:otherwise>
</c:choose>
