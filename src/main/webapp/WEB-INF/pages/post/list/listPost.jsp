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
</head>
<body>
	<div class="card text-center col-md-10 mt-5" style="margin: 0 auto;">
		<c:if test="${not empty loginSuccessMsg }">
			<div class="alert alert alert-success alert-dismissible fade show"
				role="alert">
				${loginSuccessMsg }
				<button type="button" class="close" data-dismiss="alert"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
		</c:if>
		<div class="card-header">
			<ul class="nav nav-pills card-header-pills">
				<li class="nav-item" style="margin-right: auto;"><c:url
						value="/post/search" var="searchPost" /> <form:form
						action="${searchPost }" method="POST" modelAttribute="postForm"
						cssClass="form-inline">
						<input class="form-control mr-sm-2" type="search"
							name="post_search" placeholder="Search" aria-label="Search"
							value="${searchData }">
						<button class="btn btn-outline-info my-2 my-sm-0" type="submit">Search</button>
						<a href="<c:url value="/post/download" />"
							class="btn btn-secondary ml-2">Download</a>
					</form:form></li>
				<li class="nav-item"><a class="btn text-white bg-info"
					href="<c:url value='create' />"> <b>+</b> Add
				</a></li>
			</ul>
		</div>
		<div class="row row-cols-1 row-cols-md-4 g-4" id="myList">
			<c:choose>
				<c:when test="${empty listPost }">
					<div class="col-md-12">No Data Not Found!</div>
				</c:when>
				<c:otherwise>
					<c:forEach items="${listPost }" var="post">
						<div class="col" id="myitem">
							<div class="card h-100">
								<c:if test="${ empty post.post_img }">
									<img src="../resources/img/empty.png" class="card-noimg-top"
										alt="post_img">
								</c:if>
								<c:if test="${not empty post.post_img }">
									<img src="${post.post_img }" class="card-img-top"
										alt="post_img">
								</c:if>
								<div class="cart-title font-monospace mt-2">
									<h5 class="card-title fw-bold text-black-50">${post.title }</h5>
								</div>
								<div class="card-body">
									<p class="card-text text-justify text-muted">${post.description }<br>
									</p>
								</div>
								<div class="card-footer">
									<p class="card-text text-justify text-muted">
										<c:forEach items="${post.post_categories }" var="category">
											<span class="fst-italic">${category.category_name }</span>
										</c:forEach>
										<br> <small class="text-muted">${post.date }</small>
										<c:if
											test="${(LOGIN_USER.id == post.created_user_id) || (LOGIN_USER.type == 1) }">
											<a
												href="<c:url value='/post/edit/${post.post_id }' > </c:url>"
												class="badge badge-info text-white">Edit</a>
										</c:if>
									</p>
								</div>
							</div>
						</div>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</div>
		<c:if test="${noOfPages > 0}">
			<div class="pagination-sec mt-5">
				<ul class="pagination">
					<c:if test="${currentPage != 1}">
						<li><a class="page-link"
							href="?recordsPerPage=${recordsPerPage}&currentPage=${currentPage - 1}&search_input=${searchData }">Previous</a>
						</li>
					</c:if>
					<c:forEach begin="1" end="${noOfPages }" var="i">
						<c:choose>
							<c:when test="${currentPage eq i}">
								<li><a class="page-link active"> ${i} <span
										class="sr-only">(current)</span>
								</a></li>
							</c:when>
							<c:otherwise>
								<li><a class="page-link"
									href="?recordsPerPage=${recordsPerPage}&currentPage=${i}&search_input=${searchData }">
										${i} </a></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<c:if test="${currentPage lt noOfPages}">
						<li><a class="page-link"
							href="?recordsPerPage=${recordsPerPage}&currentPage=${currentPage + 1}&search_input=${searchData }">Next</a>
						</li>
					</c:if>
				</ul>
			</div>

		</c:if>
	</div>
	<script src="<c:url value="/resources/common.js"/>"></script>
</body>
</html>