<!DOCTYPE html>
<html lang="pt-br">
<head>
<!-- Required meta tags -->
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />

<!-- Bootstrap CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css"
	integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous"
/>
<link rel="stylesheet" href="http://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css" />
<title>Users Web Admin</title>
</head>
<body>
	<nav class="navbar navbar-dark bg-dark navbar-expand-lg mb-3">
		<a class="navbar-brand" href="/">Devcase Users</a>
	</nav>
	<div class="container">
		<div id="app"></div>
	</div>


</body>
<script type="text/javascript">
	var csrftoken = {
		name  : '${_csrf.headerName}',
		token : '${_csrf.token}' 
	};
</script>
<script src="/js/webadmin.bundle.js"></script>
</html>