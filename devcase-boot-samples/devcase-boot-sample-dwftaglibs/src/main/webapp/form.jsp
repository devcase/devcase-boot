<%@page import="java.math.BigDecimal"%>
<%@page import="org.javamoney.moneta.Money"%>
<%@page import="java.time.ZoneOffset"%>
<%@page import="java.util.Date"%>
<%@page import="java.time.ZonedDateTime"%>
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
<title>Form Example</title>

<!-- Bootstrap CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css"
	integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous"
>
</head>
<body>
	<%
		pageContext.setAttribute("foundationDate",
				Date.from(ZonedDateTime.of(1970, 12, 31, 0, 0, 0, 0, ZoneOffset.systemDefault()).toInstant()));
		pageContext.setAttribute("price", Money.of(BigDecimal.valueOf(134000, 2), "BRL"));
	%>

	<jsp:useBean id="hotel" scope="page" class="br.com.devcase.boot.sample.dwftaglibs.beans.Hotel" />
	<jsp:setProperty name="hotel" property="name" value="Hotel California" />
	<jsp:setProperty name="hotel" property="foundationDate" value="${foundationDate }" />
	<jsp:setProperty name="hotel" property="rating" value="7" />
	<jsp:setProperty name="hotel" property="price" value="${price}" />

	<div class="container">
		<div class="card">
			<div class="card-body">
				<dwf:setEntity entityName="hotel" />
				<form method="post">
					<dwf:inputText property="name" />
					<dwf:inputNumber property="rating" max="10" min="1" />
					<dwf:inputMoney property="price" />
					<dwf:inputDate property="foundationDate" />
					<button type="submit" class="btn btn-primary">Submit</button>
				</form>
			</div>
		</div>
	</div>

	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
		integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"
	></script>
</body>
</html>
