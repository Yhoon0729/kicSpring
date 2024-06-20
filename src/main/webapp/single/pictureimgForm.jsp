<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>사진</title>
</head>
<body>
	<div class="container">
  <form class="form-inline" action="${pageContext.request.contextPath}/member/picturePro"   
  enctype="multipart/form-data"   method="post">
  	
	  <input type="file" class="form-control col-md-3 mb-3"  name="picture">
     <button type="submit" class="btn btn-primary col-md-3 mb-3">사진등록</button>
   
  </form>
</div>
</body>
</html>