<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 리스트</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<script
	src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
	<div class="container">
		<table class="table">
			<thead>
				<tr>
					<th>아이디</th>
					<th>비밀번호</th>
					<th>이름</th>
					<th>성별</th>
					<th>전화번호</th>
					<th>메일</th>
					<th>사진명</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="mem" items="${li}">
					<tr>
						<td>${mem.id}</td>
						<td>${mem.pass}</td>
						<td>${mem.name}</td>
						<td>${mem.gender}</td>
						<td>${mem.tel}</td>
						<td>${mem.email}</td>
						<td>${mem.picture}</td>
					</tr>
				</c:forEach>
		</table>
	</div>
</body>
</html>