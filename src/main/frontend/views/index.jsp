<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/WEB-INF/templates/meta.jspf"%>
<!-- Included meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>American Names 1890-2010</title>
<link href="/styles/sbac.min.css" rel="stylesheet">
</head>
<body id="sbac-index">
	<div class="container">
		<div class="row">
			<div class="col-xs-12">
				<h1>American Names 1890-2010</h1>
				<form id="sbac-index__search">
					<input id="sbac-index__search__input" name="query" type="text"
						placeholder="Search" autocomplete="off">
				</form>
			</div>
		</div>
	</div>
	<script src="/scripts/sbac.min.js"></script>
</body>
</html>