<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<c:url value="/password/reset/sendMail" var="resetUrl" />
	<form:form action="${resetUrl}" method="POST"
		modelAttribute="passwordResetSentMailForm">
		<div class="row content mt-5">
			<div class="col-md-6 mx-auto">
				<div class="card input-form">
					<div class="card-header text-center">Reset Passwords</div>
					<div class="card-body col-md-8 mx-auto">

						<div class="form-group">
							<label for="email" class="control-label">Email</label>
							<form:input type="text" path="user_email" class="form-control" />
							<span class="form-text text-danger"><form:errors
									path="user_email" /> </span>
							<c:if test="${not empty errorMsg }">
								<span class="text text-danger"> ${errorMsg } </span>
							</c:if>
						</div>
						<div class="col-md-12 text-center">
							<button type="submit" class="btn btn-info text-white">Send</button>
							<a href="<c:url value='/' />" class="btn btn-info text-white">
								Back </a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form:form>
</body>
</html>