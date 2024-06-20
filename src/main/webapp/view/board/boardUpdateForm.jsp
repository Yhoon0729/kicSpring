<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html >
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>회원가입 화면 샘플 - Bootstrap</title>
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<style>
body {
	min-height: 100vh;
	background: -webkit-gradient(linear, left bottom, right top, from(#92b5db),
		to(#1d466c));
	background: -webkit-linear-gradient(bottom left, #92b5db 0%, #1d466c 100%);
	background: -moz-linear-gradient(bottom left, #92b5db 0%, #1d466c 100%);
	background: -o-linear-gradient(bottom left, #92b5db 0%, #1d466c 100%);
	background: linear-gradient(to top right, #92b5db 0%, #1d466c 100%);
}

.input-form {
	max-width: 680px;
	margin-top: 80px;
	padding: 32px;
	background: #fff;
	-webkit-border-radius: 10px;
	-moz-border-radius: 10px;
	border-radius: 10px;
	-webkit-box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15);
	-moz-box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15);
	box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15)
}
</style>
<script>
function chkpass(f) {
	let chk = f.pass.value==f.pass2.value
	if (!chk) {
		alert("비밀번호 확인이 틀렸습니다")
		f.pass2.focus()
		return false
	}
	return true
}
</script>
</head> 

<body>
	<div class="container">
		<div class="input-form-backgroud row">
			<div class="input-form col-md-12 mx-auto">
				<h4 class="mb-3  text-center">${boardName}게시판수정</h4>
				<form class="validation-form" novalidate  enctype="multipart/form-data"
				    action="boardUpdatePro"   method="post"    onsubmit="return chkpass(this)">
					<input type="hidden"  name="num"  value="${board.num }"/>
					<input type="hidden"  name="originfile"  value="${board.file1 }"/>
					<div class="row">
					  
						
						<div class="col-md-12 mb-3">
							<label for="name">작성자</label> <input type="text"   name="name"
								class="form-control" id="name" placeholder="이름" value="${board.name}"   
								required>
							<div class="invalid-feedback">이름을 입력해주세요.</div>
						</div>
					</div>
					
						<div class="row">
						<div class="col-md-6 mb-3">
							<label for="pass">비밀번호</label> <input type="password"    name="pass"
								class="form-control" id="pass" placeholder="비밀번호" value="" required >
							<div class="invalid-feedback">비밀번호을 입력해주세요.</div>
						</div>
						
					</div>
					
					
					
					<div class="mb-3">
						<label for="email">제목</label> <input type="text"     name="subject"
							class="form-control" id="subject" placeholder="제목"   value="${board.subject}" }
							required>
						<div class="invalid-feedback">제목을 입력해주세요.</div>
					</div>
					<div class="mb-3">
						<label for="tel">내용</label> 
						<textarea rows="" cols=""   class="form-control" id="content" placeholder="내용"   
							required  name="content">${board.content}</textarea>
						
						<div class="invalid-feedback">내용을 입력하세요</div>
					</div>	
					
					<div class="mb-3">
						<label for="email">파일 업로드:${board.file1 }</label> 
						<input type="file"     
							class="form-control" id="file1"  name="file1"  
							>
						
					</div>
					
					
				
					<button class="btn btn-primary btn-lg btn-block" type="submit">
					저장</button>
				</form>
			</div>
		</div>
		
	</div>
	<script> window.addEventListener('load', () => { const forms = document.getElementsByClassName('validation-form'); Array.prototype.filter.call(forms, (form) => { form.addEventListener('submit', function (event) { if (form.checkValidity() === false) { event.preventDefault(); event.stopPropagation(); } form.classList.add('was-validated'); }, false); }); }, false); </script>
</body>
</html>