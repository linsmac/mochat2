<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<style>
.main-container {
	display: flex;
	flex-direction: column;
	position: relative;
	align-items: center;
	padding: 10px 0;
}

.title {
	font-size: 24px;
	margin-bottom: 16px;
}

.form-group {
	display: flex;
	flex-direction: column;
	margin-bottom: 10px;
}

.label {
	font-size: 18px;
	margin-bottom: 8px;
}

.input {
	height: 30px;
	width: 300px;
	padding: 8px;
}

.button {
	all: unset;
	display: flex;
	background-color: rgb(0, 123, 255);
	border-radius: 10px;
	color: rgb(255, 255, 255);
	cursor: pointer;
	font-size: 18px;
	height: 40px;
	width: 100px;
	text-align: center;
	margin: 0 auto;
	align-items: center;
	justify-content: center;
}

.cancel-button {
	all: unset;
	display: flex;
	background-color: rgb(83, 77, 86);
	border-radius: 10px;
	color: rgb(255, 255, 255);
	cursor: pointer;
	font-size: 18px;
	height: 40px;
	width: 100px;
	text-align: center;
	margin: 0 auto;
	align-items: center;
	justify-content: center;
}

.button-container {
	display: flex;
	justify-content: center;
	margin-top: 20px
}

.error-message {
	text-align: center;
	color: red;
	margin-top: 10px;
}

.warning {
	font-size: 13px;
	color: red;
	display: none;
}

/* 通用樣式 */
.form-group label, .form-group input {
	margin-bottom: 8px;
}
</style>

<head>
<meta charset="UTF-8">
<title>註冊帳戶</title>
</head>

<body>
	<header class="main-container">
		<h1 class="title">註冊帳號</h1>
		<form action="/Register" method="post" id="registerForm">
			<div class="form-group">
				<label for="name" class="label">姓名</label>
				<input type="text" id="name" name="name" placeholder="請輸入您的稱呼" class="input" oninput="validateNameInput(this)"/>
				<div id="nameWarning" class="warning">不可空白</div>
			</div>
			<div class="form-group">
				<label for="account" class="label">帳戶</label>
				<input type="text" id="account" name="account" placeholder="請輸入帳戶" class="input" oninput="validateInput(this)"/>
				<div id="accountWarning" class="warning">不可空白</div>
			</div>
			<div class="form-group">
				<label for="password" class="label">密碼</label>
				<input type="password" id="password" name="password" placeholder="請輸入密碼" class="input" oninput="validateInput(this)"/>
				<div id="passwordWarning" class="warning">不可空白</div>
			</div>
			<div class="form-group">
				<label for="confirmPassword" class="label">確認密碼</label>
				<input type="password" id="confirmPassword" name="confirmPassword" placeholder="請再次輸入密碼" class="input" oninput="validateInput(this)"/>
				<div id="confirmPasswordWarning" class="warning">確認密碼輸入錯誤</div>
			</div>
			<div class="button-container">
				<a href="/Login" class="cancel-button">取消</a>
				<button type="submit" class="button" onclick="submitForm(event)">註冊</button>
			</div>
		</form>
	</header>

	<script>
    function validateNameInput(input) {
        var value = input.value;
        var warning = input.nextElementSibling;

        if (value.trim() === "") {
            warning.innerText = "不可空白";
            warning.style.display = "block";
        }else {
            warning.style.display = "none";
        }
    }
	
    function validateInput(input) {
        var value = input.value;
        var warning = input.nextElementSibling;

        if (value.trim() === "") {
            warning.innerText = "不可空白";
            warning.style.display = "block";
            return;
        }

        var alphanumericRegex = /^[a-zA-Z0-9]+$/;

        if (!alphanumericRegex.test(value)) {
            warning.innerText = "只能输入英文字母和数字";
            warning.style.display = "block";
        } else {
            warning.style.display = "none";
        }
    }
    
    function submitForm(event) {
        event.preventDefault();//阻止表單默認送出的行為

        var name = document.getElementById("name").value;
        var account = document.getElementById("account").value;
        var password = document.getElementById("password").value;
        var confirmPassword = document.getElementById("confirmPassword").value;

        var nameWarning = document.getElementById("nameWarning");
        var accountWarning = document.getElementById("accountWarning");
        var passwordWarning = document.getElementById("passwordWarning");
        var confirmPasswordWarning = document.getElementById("confirmPasswordWarning");

        // 隱藏所有警告信息
        nameWarning.style.display = "none";
        accountWarning.style.display = "none";
        passwordWarning.style.display = "none";
        confirmPasswordWarning.style.display = "none";

        var hasError = false;

        // 檢查是否有空白输入
        if (name.trim() === "") {
            nameWarning.innerText = "不可空白";
            nameWarning.style.display = "block";
            hasError = true;
        }

        if (account.trim() === "") {
            accountWarning.innerText = "不可空白";
            accountWarning.style.display = "block";
            hasError = true;
        }

        if (password.trim() === "") {
            passwordWarning.innerText = "不可空白";
            passwordWarning.style.display = "block";
            hasError = true;
        }

        if (confirmPassword.trim() === "") {
            confirmPasswordWarning.innerText = "不可空白";
            confirmPasswordWarning.style.display = "block";
            hasError = true;
        }

        if (password !== confirmPassword) {
            confirmPasswordWarning.innerText = "確認密碼輸入錯誤";
            confirmPasswordWarning.style.display = "block";
            hasError = true;
        }

        if (hasError) {
            alert("資料輸入錯誤");
            return;
        } 
        var formData = new FormData();
        formData.append("name", name);
        formData.append("account", account);
        formData.append("password", password);
        formData.append("confirmPassword", confirmPassword);

        fetch("/Register", {
                method: "POST",
                body: formData
            })
            .then(response => {
                if (response.ok) {
                    return response.text();
                } else {
                    throw new Error("Network response was not ok");
                }
            })
            .then(data => {
                console.log(data);
                if (data === "00") {
                    alert("註冊成功");
                    window.location.href = "/Login";
                } else {
                    alert(data);
                }
            })
            .catch(error => {
                console.error("There was a problem with the fetch operation:", error);
            });
        
    }

	</script>
</body>
</html>
