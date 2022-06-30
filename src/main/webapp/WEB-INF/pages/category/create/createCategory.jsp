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
</head>
<body>
	<div class="row justify-content-center mt-5">
		<div class="col-md-4">
			<div class="card">
				<header class="card-header bg-color">
					<h4 class="card-title mt-2 text-center">Create Category</h4>
				</header>
				<article class="card-body">
					<c:url value="/category/createConfirm" var="createCategory"></c:url>
					<form:form action="${createCategory }"
						modelAttribute="createCategoryForm" method="POST">
						<div class="form-row">
							<div class="col form-group">
								<label>Category Name</label>
								<form:input type="text" path="category_name"
									class="form-control" placeholder=""
									value="${createCategoryForm.category_name }" />
								<form:errors path="category_name" cssClass="text-danger" />
								<br>
							</div>
							<!-- form-group end.// -->
						</div>
						<!-- form-group end.// -->
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
		<!-- col.//-->
	</div>
</body>
</html>