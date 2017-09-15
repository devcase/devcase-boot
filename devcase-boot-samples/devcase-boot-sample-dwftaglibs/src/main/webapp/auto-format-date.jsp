<%@page import="java.util.TimeZone"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.ZonedDateTime"%>
<%@page import="java.time.ZoneOffset"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://boot.devcase.com.br/dwf" prefix="dwf"%>
<!DOCTYPE html>
<html lang="pt">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>AutoFormat - Dates</title>

</head>
<body>
<% 
Date d = Date.from(ZonedDateTime.of(2017, 12, 31, 0, 0, 0, 0, ZoneOffset.systemDefault()).toInstant()); 
pageContext.setAttribute("onlyDate", d);
pageContext.setAttribute("dateTime", Date.from(ZonedDateTime.of(2017, 12, 31, 13, 32, 0, 0, ZoneOffset.systemDefault()).toInstant()));
pageContext.setAttribute("onlyTime", new Date( 13 * 60L * 60L * 1000L + 1 *  60L * 1000L)); //13:01
%>

	<div class="container">
		<div class="card">
			<div class="card-body">
				<p>Date (default): <dwf:autoFormat value="${onlyDate}"/></p>
				<p>Date (pt-BR, default timezone): <dwf:autoFormat value="${onlyDate}" locale="pt-BR"/></p>
				<p>Date (en-US, default timezone): <dwf:autoFormat value="${onlyDate}" locale="en-US"/></p>
				<p>Date (en-US, America/New_York): <dwf:autoFormat value="${onlyDate}" locale="en-US" timeZone="America/New_York"/></p>
			</div>
			<div class="card-body">
				<p>Date with Time (default): <dwf:autoFormat value="${dateTime}"/></p>
				<p>Date with Time (pt-BR, default timezone): <dwf:autoFormat value="${dateTime}" locale="pt-BR"/></p>
				<p>Date with Time (en-US, default timezone): <dwf:autoFormat value="${dateTime}" locale="en-US"/></p>
			</div>
			<div class="card-body">
				<p>Time (default): <dwf:autoFormat value="${onlyTime}"/></p>
				<p>Time (pt-BR): <dwf:autoFormat value="${onlyTime}" locale="pt-BR"/></p>
				<p>Time (en-US): <dwf:autoFormat value="${onlyTime}" locale="en-US"/></p>
			</div>
		</div>
	</div>

</body>
</html>
