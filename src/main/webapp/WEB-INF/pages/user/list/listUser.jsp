<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
div.dataTables_wrapper div.dataTables_paginate ul.pagination {
	justify-content: center;
}
</style>
</head>
<body>
	<div class="card text-center col-md-10 mt-5" style="margin: 0 auto;">
		<div class="card-header">
			<ul class="nav nav-pills card-header-pills">
				<li><input type="text" class="form form-control"
					id="userTableSearch" placeholder="Search..."></li>
				<li class="user-btn ml-3">
					<button class="btn btn-info text-white" type="button">Search</button>
				</li>
				<li class="nav-item ml-3"><a class="btn btn-info text-white"
					href="<c:url value='/user/create' />"> <b>+</b> Add
				</a></li>
			</ul>
		</div>
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
								data-bs-toggle="modal" data-user-id="n-${user.id }"
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
								href="<c:url value="/user/delete/id" />" id="user-delete-btn">OK</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			var table = $('#user-table').DataTable({
				"paging" : true,
				"pageLength" : 5,
				"bLengthChange" : false,
				"bAutoWidth" : false,
				"dom" : 'rtp'
			});
			$('#userTableSearch').keyup(function() {
				table.search($(this).val()).draw();
			});
		});

		$('.delete-btn').click(
				function() {
					$id = $(this).attr('data-user-id').substring(2);
					$url = $('#user-delete-btn').attr('href') + "";
					console.log($url);
					$user_url = $url.substring(0, $url.lastIndexOf('/') + 1)
							.concat($id);
					$('#user-delete-btn').attr('href', $user_url);
				});
	</script>
</body>
</html>