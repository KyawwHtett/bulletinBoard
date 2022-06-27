<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="row content">
	<div class="col-md-6 mx-auto">
		<c:choose>
			<c:when test="${not empty errorMsg}">
				<div class="alert alert-danger text-center" role="alert">
					${errorMsg}</div>
			</c:when>
			<c:otherwise>
				<div class="card input-form">
					<div class="card-header text-center">Enter New Password</div>
					<div class="card-body col-md-8 mx-auto">
						<c:url value="/password/changeReset" var="changeUrl" />
						<form:form action="${changeUrl}" method="POST"
							modelAttribute="passwordResetForm">
							<div class="form-group">
								<label for="password" class="control-label">New Password</label>
								<form:password path="password" class="form-control" />
								<form:hidden path="token" value="${passwordResetForm.token }" />

								<span class="form-text text-danger"> <strong><form:errors
											path="password" /></strong>
								</span>
							</div>
							<div class="col-md-12 text-center">
								<button type="submit" class="btn btn-primary">Submit</button>
							</div>
						</form:form>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
</div>