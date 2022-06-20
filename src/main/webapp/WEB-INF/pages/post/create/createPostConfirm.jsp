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
.bg-color {
	background-color: #f6f6f6;
	color: #56baed;
}

.btn-bg-color {
	background-color: #56baed;
}

textarea {
	height: 150px;
}
</style>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/gh/bbbootstrap/libraries@main/choices.min.css">
<script
	src="https://cdn.jsdelivr.net/gh/bbbootstrap/libraries@main/choices.min.js"></script>
</head>
<body>
	<div class="row justify-content-center mt-5">
		<div class="col-md-6">
			<div class="card">
				<header class="card-header bg-color">
					<h4 class="card-title mt-2 text-center">Create Post Confirm
						Form</h4>
				</header>
				<article class="card-body">
					<c:url value="/post/save" var="createPost"></c:url>
					<form:form action="${createPost }" modelAttribute="postForm"
						method="POST">
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
						<div class="form-row">
							<div class="col form-group">
								<label>Post Title</label> <input type="text"
									class="form-control" value="${postForm.title }" readonly />
								<form:hidden path="title" name="title"
									value="${postForm.title }" />
							</div>
							<!-- form-group end.// -->
						</div>
						<!-- form-row end.// -->
						<div class="form-group">
							<label>Post Description</label> <input class="form-control"
								value="${postForm.description }" readonly />
							<form:hidden path="description" name="description"
								value="${postForm.description }" />
						</div>
						<!-- form-group end.// -->
						<div class="form-group">
							<label>Post Category</label> <br>
							<c:forEach items="${resultLists }" var="cs">
								<a class="btn btn-sm btn-outline-info disabled">
									${cs.category_name } </a>
							</c:forEach>
							<%-- <form:hidden path="category" name="categories"
								value="${resultLists }" /> --%>

							<form:hidden path="category" items="${resultLists}"
								id="choices-multiple-remove-button" itemValue="category_id"
								placeholder="Select" itemLabel="category_name"
								value="${postForm.category }" />
						</div>
						<div class="form-group">
							<%-- <checkboxes items="${categoryList}" path="category"
								itemValue="category_id" itemLabel="category_name" />
							<br>
							<errors path="category" class="text-danger" /> --%>
						</div>
						<!-- form-group end.// -->
						<div class="form-group mt-3 text-center">
							<button type="submit" name="confirm" class="btn btn-bg-color">Create</button>
							<button type="button" name="back" class="btn btn-bg-color"
								onclick="history.back(2);">Backs</button>
						</div>
						<!-- form-group// -->
					</form:form>
				</article>
			</div>
			<!-- card.// -->
		</div>

	</div>
	<script type="text/javascript">
		$(document).ready(
				function() {

					var multipleCancelButton = new Choices(
							'#choices-multiple-remove-button', {
								removeItemButton : true,
								maxItemCount : 5,
								searchResultLimit : 5,
								renderChoiceLimit : 5
							});

				});
	</script>
</body>
</html>