<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<style>
.main-container {
	display: flex;
	flex-direction: column;
	position: relative;
	align-items: center;
	padding: 100px 0;
}

.title {
	font-size: 24px;
	margin-bottom: 16px;
}

.form-group {
	display: flex;
	flex-direction: column;
	margin-bottom: 16px;
}

.label {
	font-size: 18px;
	margin-bottom: 8px;
}

.input {
	height: 40px;
	width: 300px;
	padding: 8px;
}

.button {
	all: unset;
	background-color: rgb(0, 123, 255);
	border-radius: 4px;
	color: rgb(255, 255, 255);
	cursor: pointer;
	font-size: 20px;
	height: 30px;
	width: 125px;
	text-align: center;
	margin: 0 auto;
}

.register-link {
  font-size: 14px;
  font-size: 1rem;
  line-height: 20px;
  line-height: 2rem;
  font-family: "Rakuten Sans UI", "Trebuchet MS", Trebuchet, Arial, Helvetica, sans-serif;
  color: #000;
  text-decoration: underline;
  background: none; /* 移除背景色 */
  border: none;
  cursor: pointer;
  padding: 0;
  margin-top: 16px; /* 添加一些頂部間距 */
}

.button-container {
	display: flex;
	justify-content: center;
}

.error-message {
	text-align: center;
	color: red;
	margin-top: 10px;
}

/* 通用樣式 */
.form-group label, .form-group input {
	margin-bottom: 8px;
}
</style>

<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>

<body>
	<header class="main-container">
		<h1 class="title">Welcome</h1>
		<form th:action="@{/Login}" method="post" th:object="${loginForm}"
			name="loginForm">
			<div class="form-group">
				<label for="account" class="label">account</label> <input
					type="text" id="account" name=account th:field="*{account}"
					placeholder="輸入您的帳號" class="input" />
			</div>
			<div class="form-group">
				<label for="password" class="label">password</label> <input
					type="password" id="password" name=password th:field="*{password}"
					placeholder="輸入您的密碼" class="input" />
			</div>
			<c:if test="${not empty errorMessage}">
				<div class="error-message">${errorMessage}</div>
			</c:if>
			<div class="button-container">
				<button type="submit" class="button">login</button>
			</div>
			<div class="button-container">
				<a href="/Register" class="register-link">註冊帳號</a>
			</div>
		</form>
	</header>

</body>

</html>
