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
					<h4 class="card-title mt-2 text-center">Sign up</h4>
				</header>
				<article class="card-body">
					<c:url value="/register" var="userRegister"></c:url>
					<form:form action="${userRegister }"
						modelAttribute="registerUserForm" method="POST">
						<div class="form-row">
							<div class="col form-group">
								<label>User Name</label>
								<form:input type="text" path="username" class="form-control"
									placeholder="" value="${registerUserForm.username }" />
								<form:errors path="username" cssClass="text-danger" />
								<br>
							</div>
							<!-- form-group end.// -->
						</div>
						<!-- form-row end.// -->
						<div class="form-group">
							<label>Email address</label>
							<form:input type="text" path="email" class="form-control"
								placeholder="" value="${registerUserForm.email }" />
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
						<br>
						<!-- form-group end.// -->
						<div class="form-group">
							<label>Create password</label>
							<form:input class="form-control" type="password" path="password"
								value="${registerUserForm.password }" />
							<form:errors path="password" class="text-danger" />
							<br>
						</div>
						<!-- form-group end.// -->
						<div class="form-group mt-3 text-center">
							<button type="submit" class="btn btn-block btn-bg-color">
								Register Confirm</button>
						</div>
						<!-- form-group// -->
					</form:form>
				</article>
				<!-- card-body end .// -->
				<div class="border-top card-body text-center">
					Have an account? <a href='<c:url value="/" />'>Log In</a>
				</div>
			</div>
			<!-- card.// -->
		</div>
		<!-- col.//-->
	</div>
</body>
</html>