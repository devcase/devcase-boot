<%@tag import="br.com.devcase.boot.sitemesh2.ParsedHtmlPage"%>
<%@ tag trimDirectiveWhitespaces="true"%><%
Object decoratedPage = jspContext.findAttribute("dwf_decoratedPage");
if(decoratedPage != null && decoratedPage instanceof ParsedHtmlPage) {
	((ParsedHtmlPage) decoratedPage).writeBody(out);
}
%>