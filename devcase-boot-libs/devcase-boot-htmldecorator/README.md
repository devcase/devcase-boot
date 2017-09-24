# devcase-boot-htmldecorator

## Introdução

Implementação de decorador de html (como o SiteMesh), mas que, ao fazer o parse da página original, separa as tags scripts do body.

**Atenção:** A página original só pode ter tags `<script>` ao final do body.

O _decorador_ deve ser um `.jsp`, e 

```html
<%@ taglib uri="http://boot.devcase.com.br/decorator" prefix="decorator" %>
<html>
<head>
	<title><decorator:title/></title>
	<decorator:head/>
</head>
<body>
	<decorator:body/>
	<script src="/js/custom.js"></script>
	<decorator:scripts/>
</body>
<html>
```