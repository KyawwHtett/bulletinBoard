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
</style>
</head>
<body>
	<div class="card text-center col-md-8 mt-5" style="margin: 0 auto;">
		<div class="card-header">
			<ul class="nav nav-pills card-header-pills">
				<li class="nav-item"><a class="nav-link active"
					href="<c:url value='create' />"> <b>+</b> Add
				</a></li>
				<!-- <li class="nav-item"><a class="nav-link" href="#">Link</a></li>
				<li class="nav-item"><a class="nav-link disabled" href="#"
					tabindex="-1" aria-disabled="true">Disabled</a></li> -->
			</ul>
		</div>
		<div class="card-body">
			<table class="table table-hover">
				<thead class="thead-light">
					<tr>
						<th scope="col">No.</th>
						<th scope="col">Category Name</th>
						<th scope="col">Action</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${listCategory }" var="category"
						varStatus="index">
						<tr>
							<th scope="row">${index.index+1 }</th>
							<td>${category.category_name }</td>
							<td><a class="btn btn-outline-info" href='<c:url value='/category/edit?id=${category.category_id }' ></c:url>'>Edit</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>