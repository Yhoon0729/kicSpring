<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

</head>
<body>
	<div class="container">
		<h4 class="text-center">${boardName }[${count}](bid:${boardid},
			pnum:${pageNum})</h4>
		<a class="btn btn-primary float-right m-2"
			href="${pageContext.request.contextPath}/board/boardForm?boardid=${boardid}">게시판
			입력</a>

		<table class="table">
			<thead>
				<tr>
					<th>ser</th>
					<th>이름</th>
					<th>제목</th>
					<th>이미지</th>
					<th>날자</th>
					<th>조회</th>
				</tr>
			</thead>
			<tbody>
				<c:set var="boardNum" value="${boardNum}" />
				<c:forEach var="m" items="${li}">
					<tr>
						<td>${boardNum}</td>
						<c:set var="boardNum" value="${boardNum-1}" />
						<td>${m.name}</td>
						<td><a
							href="${pageContext.request.contextPath}/board/boardInfo?num=${m.num}">${m.subject}</a></td>
						<td>${m.file1}</td>
						<td>${m.regdate}</td>
						<td>${m.readcnt}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<ul class="pagination" style="justify-content: center">
			<li class="page-item  <c:if test="${start<=bottomLine}">disabled</c:if>"><a class="page-link"
				href="${pageContext.request.contextPath}/board/boardList?boardid=${boardid}&pageNum=${start-bottomLine}">Previous
			</a></li>

			<c:forEach var="p" begin="${start}" end="${end}">
				<li class="page-item"><a class="page-link"
					href="${pageContext.request.contextPath}/board/boardList?boardid=${boardid}&pageNum=${p}">${p}</a>
				</li>
			</c:forEach>

			<li class="page-item <c:if test="${end>=maxPage}">disabled</c:if>"><a class="page-link"
				href="${pageContext.request.contextPath}/board/boardList?pageNum=${start+bottomLine}">Next
			</a></li>
		</ul>

	</div>
</body>
</html>