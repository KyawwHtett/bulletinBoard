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
.card-img-top {
	max-height: 220px;
}
</style>
</head>
<body>
	Weclome Post
	<c:if test="${not empty loginSuccessMsg }">
		<span class="text-success">${loginSuccessMsg }</span>
	</c:if>
	<div class="card text-center col-md-10 mt-5" style="margin: 0 auto;">
		<div class="card-header">
			<ul class="nav nav-pills card-header-pills">
				<li class="nav-item"><a class="nav-link active"
					href="<c:url value='create' />"> <b>+</b> Add
				</a></li>
			</ul>
		</div>
		<div class="row row-cols-1 row-cols-md-4 g-4">
			<c:forEach items="${listPost }" var="post">
				<div class="col">
					<div class="card h-100">
						<img src="../resources/dd.png" class="card-img-top"
							alt="../resources/No1.jpg">
						<div class="cart-title font-monospace mt-2">
							<h5 class="card-title">${post.title }</h5>
						</div>
						<div class="card-body">
							<p class="card-text text-justify text-muted">${post.description }<br>
								<br>
								<c:forEach items="${post.post_categories }" var="category">
									<span class="fst-italic">${category.category_name }</span>
								</c:forEach>
								<br> <small class="text-muted">Last updated 3 mins
									ago</small><br>
							</p>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</body>
</html>