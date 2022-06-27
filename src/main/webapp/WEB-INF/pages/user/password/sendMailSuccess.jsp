<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div class="row content mt-5">
		<div class="col-md-6 mx-auto">
			<div class="card">
				<div class="card-body mx-auto">
					<span class="col-md-12 text-center text-success"> ${msg} </span> <br />
					<div class="col-md-12 text-center login-btn mt-3">
						<a href="<c:url value ="/"/>" class="btn btn-primary">Log In</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>