<%@tag import="br.com.devcase.boot.htmldecorator.ParsedHtmlPage"%>
<%@ tag trimDirectiveWhitespaces="true"%><%
Object decoratedPage = jspContext.findAttribute("dwf_decoratedPage");
if(decoratedPage != null && decoratedPage instanceof ParsedHtmlPage) {
	out.append(((ParsedHtmlPage) decoratedPage).getTitle());
}
%>