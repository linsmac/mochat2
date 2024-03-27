<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<style>

</style>

<head>
	<meta charset="UTF-8">
	<link type="text/css" rel="stylesheet" href="/css/chatListStyle.css">
	<title>Add Friend</title>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>

<body>
	<div class="main-container">
        <div class="top-container">
        	<div class="toolbar-container">
	            <a href="/ChatList?userId=${userId}&userName=${userName}" class="toolbar" id="chatListBtn">聊天列表</a>
	            <a href="/FriendList?userId=${userId}&userName=${userName}" class="toolbar" id="friendListBtn">好友列表</a>
	        	<a href="/AddFriend?userId=${userId}&userName=${userName}" class="press" id="addFriendBtn">加入好友</a>
 			</div>
	        <div class="logout-container">
	            <a href="/Login" class="logout-button" id="logoutBtn">登出</a>
	        </div>
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
    $.ajax({
        url: '/SearchFriend',
        type: 'POST',
        contentType: 'application/x-www-form-urlencoded',
        data:{
            searchInput: searchInput,
        },
        success: function (response) {
            displaySearchResults(JSON.parse(response)); // 將 JSON 字符串解析為 JavaScript 對象
        },
        error: function (xhr, status, error) {
            console.error('Ajax request failed:', error);
        }
    });
});

function displaySearchResults(results) {
    var resultsList = document.getElementById("resultsList");
    resultsList.innerHTML = "";

    if (results.length === 0) {
        var noResultsMessage = document.createElement("li");
        noResultsMessage.textContent = "查無結果";
        resultsList.appendChild(noResultsMessage);
        document.getElementById("searchResults").style.display = "block";
        return;
    }

    // 獲取用戶的好友列表
    var userId = "${userId}";
    var userName = "${userName}";
    $.ajax({
        url: '/getUserFriendList',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({ userId: userId }),
        success: function (friendList) {
            // 將用戶的好友列表轉換為字典，方便查找
            var friendMap = {};
            friendList.forEach(function (friend) {
                friendMap[friend.friendId] = friend.roomId;
            });

            results.forEach(function (result) {
                var listItem = document.createElement("li");
                listItem.className = "search-list";

                var infoContainer = document.createElement("div");
                infoContainer.style.display = "flex";
                infoContainer.style.alignItems = "center";

                var userDiv = document.createElement("div");
                userDiv.className = "user";
                userDiv.textContent = result.name;
                infoContainer.appendChild(userDiv);

                var accountDiv = document.createElement("div");
                accountDiv.className = "account";
                accountDiv.textContent = "帳號: " + result.account;
                infoContainer.appendChild(accountDiv);

                listItem.appendChild(infoContainer);

                var buttonContainer = document.createElement("div");
                buttonContainer.className = "button-container";
                var addButton = document.createElement("button");

                if (friendMap[result.userId]) {
                    addButton.textContent = "聊天";
                    addButton.className = "chat-button";
                    addButton.addEventListener("click", function () {
                        var roomId = friendMap[result.userId]; // 使用朋友的ID检索roomId
                        if (roomId) {
                            openChatRoom(roomId, userId, userName, result.userId, result.name);
                        } else {
                            console.error('未找到匹配的 roomId');
                        }
                    });
                } else {
                    addButton.textContent = "新增好友";
                    addButton.className = "button";
                    addButton.addEventListener("click", function () {
                        $.ajax({
                            url: '/AddNewFriend',
                            type: 'POST',
                            contentType: 'application/json',
                            data: JSON.stringify({ userId: userId, friendId: result.userId }),
                            success: function (response) {
                                console.log("新增好友成功:", response);
                                // 刷新搜索结果
                                displaySearchResults(results);
                            },
                            error: function (xhr, status, error) {
                                console.error('Ajax request failed:', error);
                            }
                        });
                    });
                }

                buttonContainer.appendChild(addButton);
                listItem.appendChild(buttonContainer);
                resultsList.appendChild(listItem);
            });
            document.getElementById("searchResults").style.display = "block";
        },
        error: function (xhr, status, error) {
            console.error('Ajax request failed:', error);
        }
    });
}

function openChatRoom(roomId, userId, userName, friendId, friendName) {
    $.ajax({
        url: '/openChatRoom',
        type: 'GET',
        data: {
            roomId: roomId,
            userId: userId,
            userName: userName,
            friendId: friendId,
            friendName: friendName
        },
        success: function (response) {
            console.log("打開聊天室成功:", response);
            var chatData = response;
            sessionStorage.setItem('chatData', JSON.stringify(chatData));
            window.location.href = '/ChatRoom?roomId=' + chatData.roomId + '&userId=' + userId + '&userName=' + userName + '&friendId=' + friendId + '&friendName=' + friendName;
        },
        error: function (xhr, status, error) {
            console.error('Ajax request failed:', error);
        }
    });
}

</script>

</body>
</html>
