<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://boot.devcase.com.br/dwf" prefix="dwf"%>
<!DOCTYPE html>
<html lang="pt">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>ResolveEL</title>
</head>
<body>
	<jsp:useBean id="exampleBean" scope="page" class="br.com.devcase.boot.sample.dwftaglibs.beans.ExampleBean"/>

	<div class="container">
		<p>Using el directly: ${exampleBean.texto}</p>
		<p>Using resolveEL tag: <dwf:resolveEL el="exampleBean.texto"/></p>
	</div>

</body>
</html>
