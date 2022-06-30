<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet"
	href="<c:url value='/resources/css/datatable.css'/>">
</head>
<body>
	<div class="card col-md-10 mt-5" style="margin: 0 auto;">
		<div class="card-header">
			<ul class="nav nav-pills card-header-pills" style="float: left;">
				<li><input type="text" class="form form-control"
					id="userTableSearch" placeholder="Search..."></li>
				<li class="user-btn ml-3">
					<button class="btn btn-info text-white" type="button">Search</button>
				</li>
				<li class="nav-item ml-3"><a class="btn btn-info text-white"
					href="<c:url value='/user/create' />"> <b>+</b> Add
				</a></li>
			</ul>
			<div style="float: right;">
				<a class="btn btn-info text-white ml-3"
					href="<c:url value='/user/export' />"> Export </a>
			</div>
		</div>
		<c:url value="/user/import?${_csrf.parameterName}=${_csrf.token}"
			var="userImport"></c:url>
		<form action="${userImport }" method="POST"
			enctype="multipart/form-data">
			<div class="col-md-4 mt-2">
				<div style="display: flex; gap: 1rem;">
					<input class="form-control" name="file" type="file" accept=".xlsx"
						id="userImportExcel"> <input type="submit" value="Import"
						class="btn btn-info text-white" />
				</div>
				<c:if test="${not empty fileImportMsg }">
					<span class="text-danger">${fileImportMsg }</span>
				</c:if>
			</div>
		</form>
		<div class="card-body">
			<table class="table table-hover" id="user-table">
				<thead class="thead-light">
					<tr>
						<th scope="col">No.</th>
						<th scope="col">User Name</th>
						<th scope="col">Email</th>
						<th scope="col">Gender</th>
						<th scope="col">Type</th>
						<th scope="col">Action</th>
					</tr>
				</thead>
				<tbody class="text-left">
					<c:forEach items="${userList }" var="user" varStatus="index">
						<tr>
							<th scope="row">${index.index+1 }</th>
							<td>${user.username }</td>
							<td>${user.email }</td>
							<td>${user.gender }</td>
							<c:if test="${user.type == '0' }">
								<td>User</td>
							</c:if>
							<c:if test="${user.type == '1' }">
								<td>Admin</td>
							</c:if>
							<td><a class="btn btn-sm btn-outline-info"
								href='<c:url value='/user/edit/${user.id }' ></c:url>'>Edit</a>
								<a class="btn btn-sm btn-outline-danger delete-btn" href="#"
								data-bs-toggle="modal" data-modal-id="n-${user.id }"
								data-bs-target="#myModal">Delete</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="modal fade" id="myModal" tabindex="-1"
				aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="exampleModalLabel">Confirm Box</h5>
							<button type="button" class="btn-close" data-bs-dismiss="modal"
								aria-label="Close"></button>
						</div>
						<div class="modal-body">Are you sure want to delete this
							user?</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-bs-dismiss="modal">Close</button>
							<a class="btn btn-danger btn-ok"
								href="<c:url value="/user/delete/id" />" id="modal-delete-btn">OK</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="<c:url value='/resources/js/datatable.js'/>"></script>
</body>
</html>