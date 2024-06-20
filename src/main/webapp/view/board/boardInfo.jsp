<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
	function commentPro(num) {
		const comment = document.querySelector("#comment").value
		if(comment == '') {
			document.querySelector("#comment").focus()
			return
		}
		const commentList = document.querySelector("#commentList")
		const url = "${pageContext.request.contextPath}/board/boardCommentPro"
		const param = '?comment='+comment+'&boardnum='+num
		
		alert(url+param)
		
		fetch(url+param)
		.then(res => res.text())
		.then(txt => {
			alert(txt)
			commentList.innerHTML=txt+" <br> "+commentList.innerHTML
		}) // end of fetch(url+param)
		
		document.querySelector("#comment").value=""
		document.querySelector("#comment").focus()
	}
	
	function enterkey(num) {
		if(window.event.keyCode == 13) { // enterkey 13번
			commentPro(num)
		}
	}
</script>
</head>
<body>

	<div class="container">
		<div class="input-form-backgroud row">
			<div class="input-form col-md-12 mx-auto">
				<h4 class="mb-3  text-center">${boardName}게시판정보</h4>
				<table class="table">

					<tr>
						<td>글번호</td>
						<td>${board.num}</td>
					<tr>
					<tr>
						<td>조회수</td>
						<td>${board.readcnt}</td>
					<tr>
					<tr>
						<td>작성자</td>
						<td>${board.name}</td>
					<tr>
					<tr>
						<td>글제목</td>
						<td>${board.subject}</td>
					<tr>
					<tr>
						<td>내용</td>
						<td>${board.content}</td>
					<tr>
					<tr>
						<td>이미지</td>
						<td><img
							src="${pageContext.request.contextPath}/img/board/${board.file1}" />
						</td>
					<tr>
					<tr class="text-center">
						<td colspan="2"><a class="btn btn-primary"
							href="${pageContext.request.contextPath}/board/boardUpdateForm?num=${board.num}">
								글수정</a> <a class="btn btn-primary"
							href="${pageContext.request.contextPath}/board/boardDeleteForm?num=${board.num}">
								글삭제</a> <a class="btn btn-primary"
							href="${pageContext.request.contextPath}/board/boardList?boardid=${sessionScope.boardid}">
								글목록</a></td>
					</tr>
				</table>

				<div class="row table">
					<div class="col-sm-1">답변글</div>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="comment" 
						onkeyup="enterkey('${board.num}')" />
					</div>
					<div class="col-sm-1">
						<button class="btn btn-primary"
							onclick="commentPro('${board.num}')">저장</button>
					</div>
				</div>

				<c:set var="count" value="${count}" />
				<div class="row table" id="commentList">
					<c:forEach var="c" items="${li}">
						<div class="col-sm-1">&nbsp;</div>
						<div class="col-sm-1">${count}</div>
						<c:set var="count" value="${count-1}" />
						<div class="col-sm-9">${c.content}</div>
						<div class="col-sm-1">&nbsp;</div>
					</c:forEach>
				</div>

			</div>
		</div>
	</div>
</body>
</html>