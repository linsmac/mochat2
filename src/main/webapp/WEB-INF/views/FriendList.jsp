<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<style>

</style>

<head>
    <meta charset="UTF-8">
    <link type="text/css" rel="stylesheet" href="/css/chatListStyle.css">
    <title>Friend List</title>
</head>

<body>
    <div class="main-container">
        <div class="toolbar-container">
            <div class="toolbar" id="chatListBtn"><a href="/ChatList?userId=${userId}"> Chat List</a></div>
            <div class="toolbar" id="friendListBtn"><a href="/FriendList?userId=${userId}">Friend List</a></div>
        </div>
        <div class="chat-list" id="chatList">
        </div>
        <div class="chat-content">
            <div class="chat-header" id="chatHeader">Select a friend</div>
            <div class="message">Click on a friend on the left to view the conversation.</div>
        </div>
    </div>

    <script>
	    // 從後端獲取 friendList JSON
	    var chatList = ${chatListJson};

	    // 將 JSON 字符串轉換為 JavaScript 對象
	   // var friendList = JSON.parse(friendListJson);

        // 動態生成聊天室條目
        var chatListElement = document.getElementById('chatList');
        chatList.forEach(function (chatRoom) {
            var roomElement = document.createElement('div');
            roomElement.className = 'chat-room';
            roomElement.innerHTML = '<div class="user">' + chatRoom.friendName + '</div>';
            roomElement.onclick = function () {
                showChatContent(chatRoom.roomId, chatRoom.userId, chatRoom.userName, chatRoom.friendId, chatRoom.friendName);
            };
            chatListElement.appendChild(roomElement);
        });

        function showChatContent(roomId, userId, userName, friendId, friendName) {
            // 將點擊的聊天室名稱發送給後端控制器
            fetch('/openChatRoom?roomId=' + roomId + '&userId=' + userId + '&userName=' + userName + '&friendId=' + friendId + '&friendName=' + friendName)
                .then(response => response.json())
                .then(data => {
                    // 處理後端返回的聊天內容，這裡可以根據實際情況更新頁面
                    console.log('Chat room opened:', data);
                    sessionStorage.setItem('chatData', JSON.stringify(data));
                    // 跳轉到聊天室頁面
                    window.location.href = '/ChatRoom?roomId=' + roomId + '&userId=' + userId + '&userName=' + userName + '&friendId=' + friendId + '&friendName=' + friendName;
                })
                .catch(error => console.error('Error opening chat room:', error));
        }
    </script>
</body>

</html>