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
	<div class="card text-center col-md-8 mt-5" style="margin: 0 auto;">
		<div class="card-header">
			<ul class="nav nav-pills card-header-pills">
				<li><input type="text" class="form form-control"
					id="categoryTableSearch" placeholder="Search..."></li>
				<li class="user-btn ml-1">
					<button class="btn btn-info text-white" type="button">Search</button>
				</li>
				<li class="nav-item ml-3"><a class="btn btn-info text-white"
					href="<c:url value='create' />"> <b>+</b> Add
				</a></li>
			</ul>
		</div>
		<div class="card-body">
			<table class="table table-hover" id="category-table">
				<thead class="thead-light">
					<tr>
						<th scope="col">No.</th>
						<th scope="col">Category Name</th>
						<th scope="col">Action</th>
					</tr>
				</thead>
				<tbody class="text-left">
					<c:forEach items="${listCategory }" var="category"
						varStatus="index">
						<tr>
							<th scope="row">${index.index+1 }</th>
							<td>${category.category_name }</td>
							<td><a class="btn btn-outline-info"
								href='<c:url value='/category/edit?id=${category.category_id }' ></c:url>'>Edit</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<script src="<c:url value='/resources/js/datatable.js'/>"></script>
</body>
</html>