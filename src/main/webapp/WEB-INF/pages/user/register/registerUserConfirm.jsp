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
					<h4 class="card-title mt-2 text-center">Sign up confirm</h4>
				</header>
				<article class="card-body">
					<c:url value="/register/confirm" var="registerUserConfirm"></c:url>
					<form:form action="${registerUserConfirm }"
						modelAttribute="registerConfirmForm" method="POST">
						<div class="form-row">
							<div class="col form-group">
								<label>User Name : </label> <span class="form-check-label">
									${registerConfirmForm.username } </span>
								<form:hidden path="username" class="form-control" placeholder=""
									value="${registerConfirmForm.username }" />
								<br>
							</div>
							<!-- form-group end.// -->
						</div>
						<!-- form-row end.// -->
						<div class="form-group">
							<label>Email address : </label> <span class="form-check-label">
								${registerConfirmForm.email } </span>
							<form:hidden path="email" class="form-control" placeholder=""
								value="${registerConfirmForm.email }" />
						</div>
						<!-- form-group end.// -->
						<div class="form-group">
							<label>Gender : </label> <span class="form-check-label">
								${registerConfirmForm.gender } </span>
							<form:hidden class="form-check-input" path="gender"
								name="gender-male" value="${registerConfirmForm.gender }" />
						</div>
						<!-- form-group end.// -->
						<div class="form-group">
							<label>Password : </label> <input type="password"
								class="form-check-label border-0"
								value="${registerConfirmForm.password }" readonly />
							<form:hidden class="form-control" path="password"
								value="${registerConfirmForm.password }" />
						</div>
						<!-- form-group end.// -->
						<!-- form-group end.// -->
						<div class="form-group mt-3 text-center">
							<button type="submit" class="btn btn-block btn-bg-color">
								Register</button>
							<button type="button" class="btn btn-block btn-bg-color"
								onclick="history.back(1);">Back</button>
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