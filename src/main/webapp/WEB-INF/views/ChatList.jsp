<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<style>

</style>

<head>
    <meta charset="UTF-8">
    <link type="text/css" rel="stylesheet" href="/css/chatListStyle.css">
    <title>Chat List</title>
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
            <div class="chat-header" id="chatHeader">Select a chat</div>
            <div class="message">Click on a chat on the left to view the conversation.</div>
        </div>
    </div>

    <script>
	    // 從後端獲取 friendList JSON
	    var chatList = ${chatListJson};
        var chatListElement = document.getElementById('chatList');
        chatList.forEach(function (chatRoom) {
            var lastText = JSON.parse(chatRoom.text);
            var lastMessage = lastText[lastText.length - 1].message;

            var roomElement = document.createElement('div');
            roomElement.className = 'chat-room';
            roomElement.innerHTML = '<div class="user">' + chatRoom.friendName + '</div>';
            roomElement.innerHTML += '<div class="last-message">' + lastMessage + '</div>';
            roomElement.onclick = function () {
                showChatContent(chatRoom.roomId, chatRoom.userId, chatRoom.friendId, chatRoom.friendName);
            };
            chatListElement.appendChild(roomElement);
        });

        function showChatContent(roomId, userId, friendId, friendName) {
            // 將點擊的聊天室名稱發送給後端控制器
            fetch('/openChatRoom?roomId=' + roomId + '&userId=' + userId + '&friendId=' + friendId + '&friendName=' + friendName)
                .then(response => response.json())
                .then(data => {
                    // 處理後端返回的聊天內容，這裡可以根據實際情況更新頁面
                    console.log('Chat room opened:', data);
                    // 跳轉到聊天室頁面
                    window.location.href = '/ChatRoom?roomId=' + roomId + '&userId=' + userId + '&friendId=' + friendId + '&friendName=' + friendName;
                })
                .catch(error => console.error('Error opening chat room:', error));
        }
    </script>
</body>

</html>