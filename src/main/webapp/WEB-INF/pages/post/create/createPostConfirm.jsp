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
	href="<c:url value='/resources/css/register.css'/>">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/gh/bbbootstrap/libraries@main/choices.min.css">
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
							<label>Post Description</label>
							<textarea class="form-control" readonly>${postForm.description }</textarea>
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

							<form:hidden path="category" items="${resultLists }"
								id="choices-multiple-remove-button" itemValue="category_id"
								placeholder="Select" itemLabel="category_name"
								value="${postForm.category }" />
						</div>
						<div class="form-group img-center">
							<c:if test="${postForm.post_img != ''}">
								<img src="${postForm.post_img }" id="post_img" class="fix-image" />
								<form:input path="post_img" type="hidden" name="post_img"
									value="${postForm.post_img }" />
							</c:if>
							<c:if test="${postForm.post_img == '' }">
								<form:hidden path="post_img" value="${postForm.post_img }" />
							</c:if>
						</div>
						<!-- form-group end.// -->
						<div class="form-group mt-3 text-center">
							<button type="submit" name="confirm" class="btn btn-bg-color">Create</button>
							<button type="submit" name="back" class="btn btn-bg-color">Back</button>
						</div>
						<!-- form-group// -->
					</form:form>
				</article>
			</div>
			<!-- card.// -->
		</div>
	</div>
	<script
		src="https://cdn.jsdelivr.net/gh/bbbootstrap/libraries@main/choices.min.js"></script>
</body>
</html>