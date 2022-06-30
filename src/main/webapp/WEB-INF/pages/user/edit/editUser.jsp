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
					<h4 class="card-title mt-2 text-center">User Edit Form</h4>
				</header>
				<article class="card-body">
					<c:url value="/user/editConfirm" var="editConfirm"></c:url>
					<form:form action="${editConfirm }" modelAttribute="userForm"
						method="POST">
						<div class="form-row">
							<div class="col form-group">
								<label>User Name</label>
								<form:input type="text" path="username" class="form-control"
									placeholder="" value="${userForm.username }" />
								<form:hidden path="id" value="${userForm.id }" />
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
								your email with anyone else.</small> <br>
							<form:errors path="email" cssClass="text-danger" />
							<c:if test="${not empty errorEmailMsg }">
								<span class="text-danger">${errorEmailMsg }</span>
							</c:if>
							<br>
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
							<c:if test="${userForm.id != LOGIN_USER.id }">
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
							</c:if>
							<c:if test="${userForm.id == LOGIN_USER.id }">
								<form:select path="type" value="${userForm.type }"
									class="form-control form-control-sm ss" disabled="true">
									<c:if test="${userForm.type == '0' || userForm.type == null }">
										<option value="0" selected>User</option>
										<option value="1">Admin</option>
									</c:if>
									<c:if test="${userForm.type == '1' }">
										<option value="0">User</option>
										<option value="1" selected>Admin</option>
									</c:if>
								</form:select>
								<form:hidden path="type" value="${userForm.type }" />
							</c:if>
						</div>
						<br>
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