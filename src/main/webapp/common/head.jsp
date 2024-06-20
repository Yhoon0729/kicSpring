<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<script
	src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>

<style>
.nav {
	color: #000;
	background-color: #f8f9fa;
	font-weight: 900;
}

/* 가운데 정렬을 위한 스타일 */
.navbar-nav {
	display: flex;
	justify-content: center;
	width: 100%;
}

/* 로고 이미지 크기 조절 */
.navbar-brand img {
	width: 50%;
}

/* 목록 스타일 */
.nav-item a.nav-link {
	color: #000;
	background-color: #f8f9fa;
}

.nav-item a.nav-link:hover {
	color: #f8f9fa;
	background-color: #000;
}
</style>
</head>
<body>
	<nav class="navbar navbar-expand-sm bg-light">
		<ul class="navbar-nav">
			<a class="navbar-brand "
				href="${pageContext.request.contextPath}/member/index"> <img
				alt="Logo" src="${pageContext.request.contextPath}/img/logo.png"
				style="width: 50%">
			</a>

			<li class="nav-item"><a
				class="nav-link ${nav eq 'index'?'nav': ' '}" href="../member/index">홈페이지</a></li>

			<c:if test="${sessionScope.id==null}">
				<li class="nav-item"><a
					class="nav-link ${nav eq 'join'?'nav':' '}"
					href="${pageContext.request.contextPath}/member/join">회원가입</a></li>
				<li class="nav-item"><a
					class="nav-link ${nav eq 'login'?'nav': ' '}"
					href="../member/login">로그인</a></li>
			</c:if>

			<c:if test="${sessionScope.id!=null}">
				<li class="nav-item"><a
					class="nav-link ${nav eq 'joininfo'?'nav': ' '}"
					href="${pageContext.request.contextPath}/member/joinInfo">
						회원정보[<%=session.getAttribute("id")%>]
				</a></li>
				<li class="nav-item"><a class="nav-link"
					href="${pageContext.request.contextPath}/member/logout">로그아웃</a></li>
			</c:if>

			<c:if test="${sessionScope.id eq 'admin'}">
				<li class="nav-item"><a class="nav-link"
					href="${pageContext.request.contextPath}/member/memberList">회원리스트</a></li>
			</c:if>

			<li class="nav-item"><a
				class="nav-link ${nav eq '1'?'nav': ' '}"
				href="${pageContext.request.contextPath}/board/boardList?boardid=1">공지사항</a></li>
			<li class="nav-item"><a
				class="nav-link ${nav eq '2'?'nav': ' '}"
				href="${pageContext.request.contextPath}/board/boardList?boardid=2">자유게시판</a></li>
			<li class="nav-item"><a
				class="nav-link ${nav eq '3'?'nav': ' '}"
				href="${pageContext.request.contextPath}/board/boardList?boardid=3">Q&A</a></li>
		</ul>
	</nav>
	<br>
</body>
</html>