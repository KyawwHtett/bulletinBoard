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
					<h4 class="card-title mt-2 text-center">User Create Confirm</h4>
				</header>
				<article class="card-body">
					<c:url value="/user/save" var="userSave"></c:url>
					<form:form action="${userSave }" modelAttribute="userForm"
						method="POST">
						<div class="form-row">
							<div class="col form-group">
								<label>User Name : </label> <span class="form-check-label">
									${userForm.username } </span>
								<form:hidden path="username" class="form-control" placeholder=""
									value="${userForm.username }" />
								<br>
							</div>
							<!-- form-group end.// -->
						</div>
						<!-- form-row end.// -->
						<div class="form-group">
							<label>Email address : </label> <span class="form-check-label">
								${userForm.email } </span>
							<form:hidden path="email" class="form-control" placeholder=""
								value="${userForm.email }" />
						</div>
						<!-- form-group end.// -->
						<div class="form-group">
							<label>Gender : </label> <span class="form-check-label">
								${userForm.gender } </span>
							<form:hidden class="form-check-input" path="gender"
								name="gender-male" value="${userForm.gender }" />
						</div>
						<div class="form-group">
							<label>Type : </label>
							<c:if test="${userForm.type == '0' }">
								<span class="form-check-label"> User </span>
							</c:if>
							<c:if test="${userForm.type == '1' }">
								<span class="form-check-label"> Admin </span>
							</c:if>
							<form:hidden class="form-check-input" path="type"
								name="gender-male" value="${userForm.type }" />
						</div>
						<!-- form-group end.// -->
						<div class="form-group">
							<label>Password : </label> <input type="password"
								class="form-check-label border-0" value="${userForm.password }"
								readonly />
							<form:hidden class="form-control" path="password"
								value="${userForm.password }" />
						</div>
						<!-- form-group end.// -->
						<div class="form-group mt-3 text-center">
							<button type="submit" name="update" class="btn btn-bg-color">Create</button>
							<button type="submit" name="back" class="btn btn-bg-color">Back</button>
						</div>
						<!-- form-group// -->
					</form:form>
				</article>
				<!-- card-body end .// -->
			</div>
			<!-- card.// -->
		</div>
		<!-- col.//-->
	</div>
</body>
</html>