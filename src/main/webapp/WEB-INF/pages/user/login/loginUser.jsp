<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<link rel="stylesheet" href="<c:url value='/resources/css/login.css'/>">
</head>
<body>
	<div class="wrapper fadeInDown">
		<div id="formContent">
			<!-- Icon -->
			<div class="fadeIn first mt-2">
				<i class="fa-solid fa-circle-user"
					style="font-size: 80px; color: #D3D3D3"></i>
			</div>

			<!-- Login Form -->
			<c:url value="/login" var="login" />
			<form:form action="${login}" method="POST" modelAttribute="loginForm">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
				<form:input type="text" path="email" value="${loginForm.email }"
					id="email" class="fadeIn second" name="email" placeholder="Email" />
				<c:if test="${not empty emptyEmail }">
					<div class="fadeIn fourth text-danger">${emptyEmail }</div>
				</c:if>
				<form:errors path="email" class="fadeIn fourth text-danger" />
				<form:input type="password" path="password"
					value="${loginForm.email }" id="password" class="fadeIn third"
					name="password" placeholder="Password" />
				<c:if test="${not empty emptyPassword }">
					<div class="fadeIn fourth text-danger">${emptyPassword }</div>
				</c:if>
				<form:errors path="password" class="fadeIn fourth text-danger" />
				<c:if test="${not empty errorMsg }">
					<div class="fadeIn fourth text-danger">${errorMsg }</div>
				</c:if>
				<input type="submit" class="fadeIn fourth" value="Log In">
			</form:form>

			<!-- Remind Password -->
			<div id="formFooter">
				<a class="underlineHover" href="<c:url value='register' />">Create
					new account?</a> <a class="underlineHover"
					href="<c:url value='/password/reset' />">Forgot Password?</a>
			</div>

		</div>
	</div>
	<script
		src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</body>
</html>