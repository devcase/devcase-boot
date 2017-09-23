<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://boot.devcase.com.br/dwf" prefix="dwf"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Import Javascript</title>

<!-- Bootstrap CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css"
	integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous"
>
</head>
<body>
	<h1>Title from html</h1>
	<dwf:importJavascript order="11">
	<script type="text/javascript">
		$(document).ready($('h1').text("altered by javascript on document ready"));
	</script>
	</dwf:importJavascript>
	<dwf:importJavascript lib="jquery"/>
	<dwf:importJavascript  lib="popper"/>
	<dwf:importJavascript  lib="bootstrap"/>
	<dwf:importJavascript  src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.4/angular.min.js" order="10"/>
	<dwf:printImportedJavascript/>
</body>
</html>
