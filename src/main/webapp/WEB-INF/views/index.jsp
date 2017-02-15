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
					<label for="sbac-index__search__input" class="sr-only">Search
						American names</label>
					<div class="input-group input-group-lg">
						<input id="sbac-index__search__input" class="form-control"
							name="query" type="text" placeholder="Search" autocomplete="off">
						<span class="input-group-btn">
							<button id="sbac-index__search__button" class="btn btn-default"
								type="submit">
								<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
							</button>
						</span>
					</div>
				</form>
				<div id="sbac-index__search-results">
					<div id="sbac-index__search-results__summary"></div>
					<table id="sbac-index__search-results__table" class="table table-condensed table-striped">
						<thead>
							<tr>
								<th>Name</th>
								<th>Gender</th>
								<th>Count</th>
								<th>Year</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<script src="/scripts/sbac.min.js"></script>
</body>
</html>