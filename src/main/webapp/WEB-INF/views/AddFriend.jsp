<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<style>
h1 {
	margin-left: 10px;
}

h3 {
	margin-left: 10px;
}

.input {
	height: 30px;
	width: 600px;
	padding: 8px;
	margin-left: 10px;
	font-size: 16px;
}

.searchButton {
	all: unset;
	display: inline-block;
	background-color: #0a2463;
	border-radius: 10px;
	color: rgb(255, 255, 255);
	cursor: pointer;
	font-size: 18px;
	height: 40px;
	width: 100px;
	text-align: center;
	margin-left: 10px;
	margin-bottom: 10px;
}

.button {
	all: unset;
	background-color: rgb(0, 123, 255);
	border-radius: 10px;
	color: rgb(255, 255, 255);
	cursor: pointer;
	font-size: 18px;
	height: 40px;
	width: 100px;
	text-align: center;
	margin-bottom: 10px;
}

#resultsList {
	list-style-type: none;
	padding: 0;
	margin: 0;
}

.chat-room {
	border-bottom: 1px solid #ccc;
	padding: 10px;
	display: flex;
	align-items: center;
}

.chat-room:hover {
	background-color: #f5f5f5;
}

.user {
	font-weight: bold;
	margin-right: 10px;
}

.account {
	font-size: 14px;
	color: #666;
}

.button-container {
	margin-left: auto;
	margin-right: 10px;
}
</style>

<head>
<meta charset="UTF-8">
<link type="text/css" rel="stylesheet" href="/css/chatListStyle.css">
<title>Add Friend</title>
</head>

<body>
	<div class="main-container">
		<div class="toolbar-container">
            <div class="toolbar" id="chatListBtn"><a href="/ChatList?userId=${userId}"> Chat List</a></div>
            <div class="toolbar" id="friendListBtn"><a href="/FriendList?userId=${userId}">Friend List</a></div>
        	<div class="toolbar" id="addFriendBtn"><a href="/AddFriend?userId=${userId}">Add Friend</a></div>
		</div>
		<h1>搜尋好友</h1>
		<form id="addFriendForm">
			<input type="text" id="searchInput" name="searchInput"
				placeholder="請輸入姓名/帳號" class="input">
			<button type="submit" class="searchButton">搜尋</button>
		</form>
		<div id="searchResults" style="display: none;">
			<h3>搜尋結果</h3>
			<ul id="resultsList"></ul>
		</div>
	</div>
	
	<script>
    document.getElementById("addFriendForm").addEventListener("submit", function(event) {
        event.preventDefault();
        var searchInput = document.getElementById("searchInput").value;
        console.log("搜尋查詢:", searchInput);
        var searchResults = ["好友1", "好友2", "好友3"];
        displaySearchResults(searchResults);
    });

    function displaySearchResults(results) {
        var resultsList = document.getElementById("resultsList");
        resultsList.innerHTML = "";
        results.forEach(function(result) {
            var listItem = document.createElement("li");
            listItem.className = "chat-room";
            
            var infoContainer = document.createElement("div");
            infoContainer.style.display = "flex";
            infoContainer.style.alignItems = "center";

            var userDiv = document.createElement("div");
            userDiv.className = "user";
            userDiv.textContent = result;
            infoContainer.appendChild(userDiv);

            var accountDiv = document.createElement("div");
            accountDiv.className = "account";
            accountDiv.textContent = "帳號: " + result.toLowerCase();
            infoContainer.appendChild(accountDiv);

            listItem.appendChild(infoContainer);

            var buttonContainer = document.createElement("div");
            buttonContainer.className = "button-container";
            var addButton = document.createElement("button");
            addButton.textContent = "新增好友";
            addButton.className = "button";
            addButton.addEventListener("click", function() {
                console.log("新增好友:", result);
            });
            buttonContainer.appendChild(addButton);
            listItem.appendChild(buttonContainer);
            resultsList.appendChild(listItem);
        });
        document.getElementById("searchResults").style.display = "block";
    }
    </script>
</body>
</html>
