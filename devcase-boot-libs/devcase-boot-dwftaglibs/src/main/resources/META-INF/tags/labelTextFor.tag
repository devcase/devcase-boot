<%@tag import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@tag import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%@tag import="java.util.Locale"%>
<%@tag import="org.springframework.context.MessageSource"%>
<%@tag import="org.springframework.web.context.WebApplicationContext"%>
<%@tag import="org.apache.commons.lang3.StringUtils"%>
<%@ tag trimDirectiveWhitespaces="true" body-content="empty" description="Finds the label for an entity property"%>
<%@ attribute name="property" required="true" type="java.lang.String" %>
<%@ attribute name="var" required="false" type="java.lang.String" %>
<%
	WebApplicationContext webApplicationContext = WebApplicationContextUtils
			.getRequiredWebApplicationContext(request.getServletContext());
	MessageSource messageSource = webApplicationContext;
	Locale locale = RequestContextUtils.getLocale(request);

	String entityName = (String) getJspContext().findAttribute("entityName");

	//definir label
	String label = "";
	if (StringUtils.isNotBlank(property)) {
		label = messageSource.getMessage(entityName.concat(".").concat(property), null, "", locale);
	}
	if (StringUtils.isBlank(label) && StringUtils.isNotBlank(property)) {
		//não achou no message source
		label = property;
		if (label.length() > 0) {
			label = label.replaceAll("(\\p{Ll})(\\p{Lu})", "$1 $2");
			label = (char) ((int) label.charAt(0) + ('A' - 'a')) + label.substring(1);
		}
	}
	
	if(StringUtils.isNotBlank(var)) {
		getJspContext().setAttribute(var, label);
		return;
	}
	getJspContext().getOut().write(label);
%>