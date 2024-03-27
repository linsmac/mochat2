<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<style>

</style>

<head>
	<meta charset="UTF-8">
	<link type="text/css" rel="stylesheet" href="/css/loginStyle.css">
	<title>Login</title>
</head>

<body>
	<header class="login-main-container">
		<h1 class="title">Welcome</h1>
		<form th:action="@{/Login}" method="post" th:object="${loginForm}"
			name="loginForm">
			<div class="form-group">
				<label for="account" class="label">帳戶</label>
				<input type="text" id="account" name=account th:field="*{account}" placeholder="輸入您的帳號" class="input" oninput="validateInput(this)"/>
				<div id="accountWarning" class="warning">不可空白</div>
			</div>
			<div class="form-group">
				<label for="password" class="label">密碼</label>
				<input type="password" id="password" name=password th:field="*{password}" placeholder="輸入您的密碼" class="input" oninput="validateInput(this)"/>
				<div id="passwordWarning" class="warning">不可空白</div>
			</div>
			<div class="button-container">
				<button type="submit" class="button" onclick="submitForm(event)">登入</button>
			</div>
			<div class="button-container">
				<a href="/Register" class="register-link">註冊帳號</a>
			</div>
		</form>
	</header>
	
	<script>
	
    function validateInput(input) {
        var value = input.value;
        var input = input;
        var warning = input.nextElementSibling;
        var alphanumericRegex = /^[a-zA-Z0-9]+$/;

        
        if (value.includes(" ")) {
            input.classList.add("error-input");
            warning.innerText = "不可輸入空格";
            warning.style.display = "block";
        } else if (value.trim() === "") {
        	input.classList.remove("error-input");
            warning.style.display = "none";
        } else if (!alphanumericRegex.test(value)) {
        	input.classList.add("error-input");
            warning.innerText = "只能輸入英文字母和數字";
            warning.style.display = "block";
        } else {
        	input.classList.remove("error-input");
            warning.style.display = "none";
        }
    }

	// 提交表單時再次檢查
	function submitForm(event) {
	    event.preventDefault(); // 阻止表單默認送出的行為

	    var account = document.getElementById("account").value;
	    var password = document.getElementById("password").value;

	    var accountWarning = document.getElementById("accountWarning");
	    var passwordWarning = document.getElementById("passwordWarning");

	    accountWarning.style.display = "none";
	    passwordWarning.style.display = "none";

	    var hasError = false; // 添加一個標誌來標記是否有錯誤
	    var alphanumericRegex = /^[a-zA-Z0-9]+$/;
	    var accountInput = document.getElementById("account");
	    var passwordInput = document.getElementById("password");

	    if (account.includes(" ")) {
	        accountInput.classList.add("error-input");
	        accountWarning.innerText = "不可輸入空格";
	        accountWarning.style.display = "block";
	        hasError = true;
	    } else if (account.trim() === "") {
        	accountInput.classList.add("error-input");
        	accountWarning.innerText = "不可空白";
        	accountWarning.style.display = "block";
        	hasError = true;
        } else if (!alphanumericRegex.test(account)) {
   	        var accountInput = document.getElementById("account");
   	        accountInput.classList.add("error-input");
            accountWarning.innerText = "只能輸入英文字母和數字";
            accountWarning.style.display = "block";
            hasError = true;
        }

	    if (password.includes(" ")) {
	        passwordInput.classList.add("error-input");
	        passwordWarning.innerText = "不可輸入空格";
	        passwordWarning.style.display = "block";
	        hasError = true;
	    }else if (password.trim() === "") {
	    	passwordInput.classList.add("error-input");
	    	passwordWarning.innerText = "不可空白";
	    	passwordWarning.style.display = "block";
        	hasError = true;
	    }

	    // 如果沒有錯誤，則提交表單
	    if (!hasError) {
	        document.forms["loginForm"].submit(); // 提交表單
	    }
	}
	
	// 從後端返回的錯誤消息
	var errorMessage = "${errorMessage}";

	if (errorMessage === "密碼錯誤") {
	    var passwordInput = document.getElementById("password");
	    passwordInput.classList.add("error-input"); // 將密碼輸入框添加到 error-input 類
	    var passwordWarning = document.getElementById("passwordWarning");
	    passwordWarning.innerText = "密碼錯誤";
	    passwordWarning.style.display = "block";
	} else if (errorMessage === "查無此帳號") {
	    alert(errorMessage);
	    window.location.href = "/Register"; // 跳轉到註冊頁面
	}
	
	</script>
</body>

</html>
