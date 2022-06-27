<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap-theme.min.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/js/all.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
	<security:authorize access="hasAnyRole('USER', 'ADMIN')"
		var="isLoggedin" />
	<c:if test="${isLoggedin}">
		<nav class="navbar navbar-expand-lg navbar-dark bg-info">
			<div class="container">
				<button class="navbar-toggler" type="button" data-toggle="collapse"
					data-target="#navbarTogglerDemo01"
					aria-controls="navbarTogglerDemo01" aria-expanded="false"
					aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
				<a class="navbar-brand" href="#"> <i class="fa-solid fa-blog"
					style="font-size: 2em;"></i>
				</a>

				<c:choose>
					<c:when test="${isLoggedin}">
						<div class="collapse navbar-collapse" id="navbarTogglerDemo01"
							style="justify-content: space-between;">
							<c:choose>
								<c:when test="${LOGIN_USER.type == '0'}">
									<ul class="navbar-nav mr-auto mt-2 mt-lg-0">
										<li class="nav-item active"><a class="nav-link"
											href="<c:url value="/post/list" />">Home </a></li>
									</ul>
									<ul class="navbar-nav ">
										<!-- PROFILE DROPDOWN - scrolling off the page to the right -->
										<li class="nav-item dropdown"><a href="#"
											class="nav-link dropdown-toggle" id="navDropDownLink"
											data-toggle="dropdown" aria-haspopup="true"
											aria-expanded="false"><i class="fa-solid fa-user"></i>
												${LOGIN_USER.username } </a>
											<div class="dropdown-menu" aria-labelledby="navDropDownLink">
												<c:url value="/user/profile/${LOGIN_USER.id }"
													var="profileDetail" />
												<a class="dropdown-item" href="${profileDetail}">Profile</a>
												<div class="dropdown-divider"></div>
												<c:url value="/logout" var="logoutUrl" />
												<a class="dropdown-item" href="${logoutUrl}">Logout</a>
											</div></li>
									</ul>
								</c:when>
								<c:otherwise>
									<ul class="navbar-nav mr-auto mt-2 mt-lg-0">
										<li class="nav-item active"><a class="nav-link"
											href="<c:url value="/post/list" />">Home </a></li>
										<li class="nav-item active"><a class="nav-link"
											href="${pageContext.request.contextPath}/post/list">Posts
										</a></li>
										<li class="nav-item active"><a class="nav-link"
											href="${pageContext.request.contextPath}/category/list">
												Category </a></li>
										<li class="nav-item active"><a class="nav-link"
											href="${pageContext.request.contextPath}/user/list">
												Users </a></li>
									</ul>
									<!-- <form id="searchForm" class="form-inline my-2 my-lg-0">
							                <input class="form-control mr-sm-2" type="search"
							                  placeholder="Search" aria-label="Search">
							                <button class="btn btn-outline-success my-2 my-sm-0"
							                  type="submit">Search</button>
							              </form> -->
									<ul class="navbar-nav ">
										<!-- PROFILE DROPDOWN - scrolling off the page to the right -->
										<li class="nav-item dropdown active"><a href="#"
											class="nav-link dropdown-toggle" id="navDropDownLink"
											data-toggle="dropdown" aria-haspopup="true"
											aria-expanded="false"><i class="fa-solid fa-user"></i>
												${LOGIN_USER.username } </a>
											<div class="dropdown-menu" aria-labelledby="navDropDownLink">
												<c:url value="/user/profile/${LOGIN_USER.id }"
													var="profileDetail" />
												<a class="dropdown-item" href="${profileDetail}">Profile</a>
												<div class="dropdown-divider"></div>
												<c:url value="/logout" var="logoutUrl" />
												<a class="dropdown-item" href="${logoutUrl}">Logout</a>
											</div></li>
									</ul>
								</c:otherwise>
							</c:choose>
						</div>
					</c:when>
					<c:otherwise>
						<div class="collapse navbar-collapse" id="navbarTogglerDemo01">
							<ul class="navbar-nav mr-auto mt-2 mt-lg-0">
							</ul>
							<ul class="navbar-nav ">
								<!-- PROFILE DROPDOWN - scrolling off the page to the right -->
								<li class="nav-item dropdown active"><a href="#"
									class="nav-link dropdown-toggle" id="navDropDownLink"
									data-toggle="dropdown" aria-haspopup="true"
									aria-expanded="false"><i class="fa-solid fa-list-check"></i>Settings</a>
									<div class="dropdown-menu" aria-labelledby="navDropDownLink">
										<a class="dropdown-item" href="#">Login</a>
										<div class="dropdown-divider"></div>
										<a class="dropdown-item" href="#">Register</a>
									</div></li>
							</ul>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</nav>
	</c:if>
</body>
</html>