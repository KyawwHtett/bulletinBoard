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
					<h4 class="card-title mt-2 text-center">Create Post Form</h4>
				</header>
				<article class="card-body">
					<c:url value="/post/createConfirm" var="createPost"></c:url>
					<form:form action="${createPost }" modelAttribute="postForm"
						method="POST">
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
						<div class="form-row">
							<div class="col form-group">
								<label>Post Title</label>
								<form:input type="text" path="title" class="form-control"
									placeholder="" value="${postForm.title }" />
								<form:errors path="title" cssClass="text-danger" />
							</div>
							<!-- form-group end.// -->
						</div>
						<!-- form-row end.// -->
						<div class="form-group">
							<label>Post Description</label>
							<form:textarea type="textarea" path="description"
								class="form-control" placeholder=""
								value="${postForm.description }" />
							<form:errors path="description" cssClass="text-danger" />
						</div>
						<!-- form-group end.// -->
						<div class="form-group">
							<label>Post Category</label>
							<form:select path="category" items="${categoryList}"
								id="choices-multiple-remove-button" itemValue="category_id"
								placeholder="Select" itemLabel="category_name"
								value="${postForm.category }" />
							<form:errors path="category" class="text-danger" />
						</div>
						<!-- form-group end.// -->
						<div class="form-group">
							<label for="image">Image Upload</label>
							<div style="display: flex; justify-content: flex-start;">
								<div>
									<input type="file" name="fileUpload" id="fileUpload"
										accept="image/*" value="${imageData }"
										class="input image-input" onchange="showImage.call(this)" />
									<input name="imageData" type="hidden" id="imageData" value="" />
								</div>
								<div>
									<img src="${postForm.post_img}" id="post_img" />
								</div>
							</div>
							<form:input id="post-image" path="post_img" type="hidden"
								value="${imageData}" />
						</div>
						<div class="form-group mt-3 text-center">
							<button type="submit" name="confirm" class="btn btn-bg-color">Confirm</button>
							<button type="submit" name="back" class="btn btn-bg-color">Back</button>
						</div>
						<!-- form-group// -->
					</form:form>
				</article>
			</div>
			<!-- card.// -->
		</div>

	</div>
	<script src="<c:url value='/resources/js/fileUpload.js'/>"></script>
	<script
		src="https://cdn.jsdelivr.net/gh/bbbootstrap/libraries@main/choices.min.js"></script>
</body>
</html>