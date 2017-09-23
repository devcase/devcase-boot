<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://boot.devcase.com.br/dwf" prefix="dwf"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Add Javascript</title>

<!-- Bootstrap CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css"
	integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous"
>
</head>
<body>
	<h1>HI!</h1>
	<dwf:addJavascript lib="bootstrap"/>
	<dwf:addJavascript lib="jquery"/>
	<dwf:addJavascript lib="bootstrap"/>
	<dwf:addJavascript src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.4/angular.min.js" order="10"/>
	<dwf:addJavascript lib="bootstrap"/>
	<dwf:addJavascript lib="bootstrap"/>
	<dwf:addJavascript order="11">
	<script type="text/javascript">
		alert("hello");
	</script>
	</dwf:addJavascript>
	<dwf:addJavascript src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.4/angular.min.js" order="10"/>
	<dwf:printAddedJavascript/>
</body>
</html>
