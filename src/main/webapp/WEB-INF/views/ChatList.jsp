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
        <div class="top-container">
        	<div class="toolbar-container">
	            <a href="/ChatList?userId=${userId}&userName=${userName}" class="press" id="chatListBtn">聊天列表</a>
	            <a href="/FriendList?userId=${userId}&userName=${userName}" class="toolbar" id="friendListBtn">好友列表</a>
	        	<a href="/AddFriend?userId=${userId}&userName=${userName}" class="toolbar" id="addFriendBtn">加入好友</a>
 			</div>
	        <div class="logout-container">
	            <a href="/Login" class="logout-button" id="logoutBtn">登出</a>
	        </div>
 		</div>
        <div class="chat-list" id="chatList">
        </div>
        <div class="chat-content">
            <div class="chat-header" id="chatHeader">Select a chat</div>
            <div class="message">Click on a chat on the left to view the conversation.</div>
        </div>
    </div>

    <script>    
	    window.onload = function() {
	        // 檢查是否是通過返回鍵返回的
	        var checkFromChatRoom = sessionStorage.getItem('checkFromChatRoom');
				if (checkFromChatRoom) {
	
	            // 執行程式碼
	            var chatData = JSON.parse(sessionStorage.getItem('chatData'));
	            var userId = ${userId};
	            var userName = chatData.userName;
	            window.location.href = '/ChatList?userId=' + userId + '&userName=' + userName;
	            
	            // 設置標記，表示已經執行過程式碼
	            sessionStorage.removeItem('checkFromChatRoom');
	        }
	    };
	    
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