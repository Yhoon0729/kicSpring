<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>홈페이지</title>
<style>
body {
	font-family: Arial, sans-serif;
	margin: 0;
	padding: 0;
	background-color: #f0f0f0;
}

header {
	background-color: #333;
	color: #fff;
	padding: 10px 0;
	text-align: center;
}

section {
	padding: 20px;
	background-color: #fff;
	margin: 10px;
}

footer {
	background-color: #333;
	color: #fff;
	text-align: center;
	padding: 10px 0;
}
</style>
</head>
<body>
	<header>
		<h1>PlayTime ~!</h1>
	</header>
	<section>
		<h2>내용</h2>
		<p>Lorem ipsum dolor sit amet consectetur adipisicing elit.
			Accusantium reiciendis saepe id ut. Corrupti odio asperiores quasi
			officia corporis repellat ut animi rem magnam dolorum atque tempore,
			officiis repellendus est?</p>
	</section>

	<section>
		<h2>오늘의 운세 !!</h2>
		<form action="fortunePro" method="post">
			이름: <input type="text" name="name" placeholder=""> <input type="submit"
				value="운세 보기">
		</form>
	</section>

	<footer>
		<p>&copy; 2024 YH</p>
	</footer>
</body>
</html>