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
<title>InputText</title>

<!-- Bootstrap CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css"
	integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous"
>

</head>
<body>
	<jsp:useBean id="hotel" scope="page" class="br.com.devcase.boot.sample.dwftaglibs.beans.Hotel" />
	<jsp:setProperty property="name" name="hotel" value="Hotel California" />

	<div class="container">
		<div class="card">
			<div class="card-body">
				<form>
					<dwf:setEntity entityName="hotel" />
					<dwf:inputText property="name" />
				</form>

			</div>
		</div>
	</div>

</body>
</html>
