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
<title>AutoFormat</title>

</head>
<body>
	<jsp:useBean id="hotel" scope="page" class="br.com.devcase.boot.sample.dwftaglibs.beans.Hotel" />
	<jsp:setProperty property="name" name="hotel" value="Hotel California" />

	<div class="container">
		<div class="card">
			<div class="card-body">
				<p><dwf:autoFormat value="${hotel.name}"/></p>
			</div>
		</div>
	</div>

</body>
</html>
