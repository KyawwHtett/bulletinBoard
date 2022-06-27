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
</style>
</head>
<body>
	<div class="row justify-content-center mt-5">
		<div class="col-md-4">
			<div class="card">
				<header class="card-header bg-color">
					<h4 class="card-title mt-2 text-center">User Create Form</h4>
				</header>
				<c:url value="/user/createConfirm" var="userCreateConfirm"></c:url>
				<form:form action="${userCreateConfirm }" modelAttribute="userForm"
					method="POST">
					<article class="card-body">
						<div class="form-row">
							<div class="col form-group">
								<label>User Name</label>
								<form:input type="text" path="username" class="form-control"
									placeholder="" value="${userForm.username }" />
								<form:errors path="username" cssClass="text-danger" />
								<br>
							</div>
							<!-- form-group end.// -->
						</div>
						<!-- form-row end.// -->
						<div class="form-group">
							<label>Email address</label>
							<form:input type="text" path="email" class="form-control"
								placeholder="" value="${userForm.email }" />
							<small class="form-text text-muted">We'll never share
								your email with anyone else.</small>
							<form:errors path="email" cssClass="text-danger" />
							<c:if test="${not empty errorEmailMsg }">
								<span class="text-danger">${errorEmailMsg }</span>
							</c:if>
						</div>
						<!-- form-group end.// -->
						<div class="form-group">
							<label class="form-check form-check-inline"> <form:radiobutton
									class="form-check-input" path="gender" name="gender-male"
									value="Male" checked="checked" /> <span
								class="form-check-label"> Male </span>
							</label> <label class="form-check form-check-inline"> <form:radiobutton
									class="form-check-input" path="gender" name="gender-female"
									value="Female" /> <span class="form-check-label">
									Female</span>
							</label>
						</div>
						<div class="form-group">
							<label>Type</label>
							<form:select path="type" value="${userForm.type }"
								class="form-control form-control-sm">
								<c:if test="${userForm.type == '0' || userForm.type == null }">
									<option value="0" selected>User</option>
									<option value="1">Admin</option>
								</c:if>
								<c:if test="${userForm.type == '1' }">
									<option value="0">User</option>
									<option value="1" selected>Admin</option>
								</c:if>
							</form:select>
						</div>
						<!-- form-group end.// -->
						<div class="form-group">
							<label>Create password</label>
							<form:input class="form-control" type="password" path="password"
								value="${userForm.password }" />
							<form:errors path="password" class="text-danger" />
						</div>
						<!-- form-group end.// -->
					</article>
					<div class="card-footer">
						<div class="form-group mt-3 text-center">
							<button type="submit" name="confirm" class="btn btn-bg-color">Confirm</button>
							<button type="submit" name="back" class="btn btn-bg-color">Back</button>
						</div>
						<!-- form-group// -->
					</div>
				</form:form>
			</div>
			<!-- card.// -->
		</div>
		<!-- col.//-->

	</div>
</body>
</html>