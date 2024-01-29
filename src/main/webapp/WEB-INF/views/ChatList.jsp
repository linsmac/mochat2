<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<style>
  body {
    margin: 0;
    padding: 0;
    font-family: Arial, sans-serif;
  }

  .main-container {
    display: flex;
    flex-direction: column;
    height: 100vh; /* 使主容器占據整個視窗高度 */
  }

  .toolbar {
    background-color: rgb(30, 135, 240);
    color: white;
    padding: 10px;
    text-align: center;
  }

  .chat-list {
    display: flex;
    flex-direction: column;
    overflow-y: auto;
    flex: 1; /* 使聊天列表占據剩餘的高度 */
  }

  .chat-room {
    border-bottom: 1px solid rgb(240, 240, 240);
    padding: 10px;
    cursor: pointer;
  }

  .chat-room:hover {
    background-color: rgb(245, 245, 245);
  }

  .chat-room .user {
    font-size: 18px;
    font-weight: bold;
  }

  .chat-room .last-message {
    color: rgb(102, 102, 102);
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  }

  .chat-content {
    padding: 20px;
  }

  .chat-header {
    font-size: 24px;
    font-weight: bold;
    margin-bottom: 10px;
  }

  .message {
    margin-bottom: 10px;
  }

</style>

<head>
    <meta charset="UTF-8">
    <title>Chat List</title>
</head>

<body>
    <div class="main-container">
        <div class="toolbar">Chat List</div>
        <div class="chat-list" id="chatList">
            <!-- 聊天室條目將動態插入此處 -->
        </div>
        <div class="chat-content">
            <div class="chat-header" id="chatHeader">Select a chat</div>
            <div class="message">Click on a chat on the left to view the conversation.</div>
        </div>
    </div>

    <script>
        // 模擬後端返回的聊天列表資料
        var chatData = [
            { username: 'User 1', lastMessage: 'Hello there!' },
            { username: 'User 2', lastMessage: 'How are you?' },
            { username: 'User 3', lastMessage: 'Good to see you!' }
            // 其他聊天室條目
        ];

        // 動態生成聊天室條目
        var chatListElement = document.getElementById('chatList');
        chatData.forEach(function (chatRoom) {
            var roomElement = document.createElement('div');
            roomElement.className = 'chat-room';
            roomElement.innerHTML = '<div class="user">' + chatRoom.username + '</div>' +
                '<div class="last-message">' + chatRoom.lastMessage + '</div>';
            roomElement.onclick = function () {
                showChatContent(chatRoom.username);
            };
            chatListElement.appendChild(roomElement);
        });

        function showChatContent(username) {
            // 將點擊的聊天室名稱發送給後端控制器
            fetch('/openChatRoom?username=' + username)
                .then(response => response.json())
                .then(data => {
                    // 處理後端返回的聊天內容，這裡可以根據實際情況更新頁面
                    console.log('Chat room opened:', data);
                    // 跳轉到聊天室頁面
                    window.location.href = '/ChatRoom';
                })
                .catch(error => console.error('Error opening chat room:', error));
        }
    </script>
</body>

</html>
