<!-- 聊天室頁面 -->
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
    display: flex;
    justify-content: space-between; /* 將內容分散對齊 */
    align-items: center;
  }

  .chat-content {
    flex: 1;
    overflow-y: auto;
    padding: 20px;
    display: flex;
    flex-direction: column;
    align-items: flex-start; /* 將內容靠左對齊 */
  }

  .chat-header {
    font-size: 24px;
    font-weight: bold;
    margin-bottom: 10px;
  }

  .message-container {
    display: flex;
    flex-direction: column;
    max-width: 70%; /* 設置最大寬度以防止消息過寬 */
    margin-bottom: 20px;
    align-self: flex-start; /* 將容器靠左對齊 */
  }

  .message {
    padding: 10px;
    border-radius: 10px;
    margin-bottom: 5px;
    word-wrap: break-word; /* 防止長字串擠爆容器 */
  }

  .message-sender {
    font-weight: bold;
    margin-bottom: 5px;
  }

  .message-time {
    font-size: 12px;
    color: #777;
    text-align: right;
  }

  .input-container {
    display: flex;
    margin-top: 10px;
  }

  .message-input {
    flex: 1;
    padding: 8px;
    font-size: 16px;
  }

  .send-button {
    background-color: rgb(30, 135, 240);
    color: white;
    border: none;
    padding: 8px 16px;
    cursor: pointer;
  }

  .close-button {
    background-color: rgb(255, 69, 0);
    color: white;
    border: none;
    padding: 8px 16px;
    cursor: pointer;
  }

</style>

<head>
    <meta charset="UTF-8">
    <title>Chat Room</title>
</head>

<body>
    <div class="main-container">
        <div class="toolbar" id="toolbar">
            <div class="chat-header" id="chatHeader">Chat Room</div>
            <button class="close-button" onclick="closeChat()">×</button>
        </div>
        <div class="chat-content" id="chatContent">
            <!-- 過去的歷史消息將動態插入此處 -->
        </div>
        <div class="input-container">
            <input type="text" class="message-input" id="messageInput" placeholder="Type your message..."
                onkeydown="handleKeyDown(event)">
            <button class="send-button" onclick="sendMessage()">Send</button>
        </div>
    </div>

    <script>
        function sendMessage() {
            var messageInput = document.getElementById('messageInput');
            var message = messageInput.value;
            if (message.trim() !== '') {
                // 在此處添加發送消息的邏輯，並更新 .chat-content 的內容
                var chatContent = document.getElementById('chatContent');
                var messageContainer = document.createElement('div');
                messageContainer.className = 'message-container';

                var messageElement = document.createElement('div');
                messageElement.className = 'message';
                messageElement.innerText = message;

                var senderElement = document.createElement('div');
                senderElement.className = 'message-sender';
                senderElement.innerText = 'You'; // 假設當前使用者名稱為 "You"

                var timeElement = document.createElement('div');
                timeElement.className = 'message-time';
                var currentTime = new Date();
                timeElement.innerText = formatTime(currentTime);

                messageContainer.appendChild(senderElement);
                messageContainer.appendChild(messageElement);
                messageContainer.appendChild(timeElement);

                chatContent.appendChild(messageContainer);

                // 清空輸入框
                messageInput.value = '';

                // 滾動至最新消息處
                chatContent.scrollTop = chatContent.scrollHeight;
            }
        }

        function closeChat() {
            // 在此處添加關閉聊天室的邏輯，可以使用 window.location.href 或其他方式
            window.location.href = '/ChatList';
        }

        function setChatHeader(chatObjectName) {
            // 設置最上方的聊天對象名稱
            var chatHeader = document.getElementById('chatHeader');
            chatHeader.innerText = chatObjectName;
        }

        function formatTime(date) {
            // 格式化時間為 hh:mm
            var hours = date.getHours();
            var minutes = date.getMinutes();
            return hours + ':' + (minutes < 10 ? '0' : '') + minutes;
        }

        function handleKeyDown(event) {
            // 監聽 Enter 鍵事件
            if (event.key === 'Enter') {
                event.preventDefault(); // 防止換行
                sendMessage(); // 觸發發送消息函數
            }
        }

        // 假設點擊了聊天室列表中的某個聊天室，將聊天對象名稱傳遞給 setChatHeader 函數
        setChatHeader('John Doe'); // 假設聊天對象是 John Doe

        // 假設從後端取得了過去的歷史消息數據，將它們插入到 .chat-content 中
        var chatContent = document.getElementById('chatContent');
        var historyMessages = [
            { sender: 'John Doe', message: 'Hello!', time: new Date('2024-01-25T12:34:56') },
            { sender: 'You', message: 'Hi John!', time: new Date('2024-01-25T12:35:00') },
            // ... 其他歷史消息
        ];

        historyMessages.forEach(function (msg) {
            appendMessage(msg.sender, msg.message, msg.time);
        });

        function appendMessage(sender, message, time) {
            var messageContainer = document.createElement('div');
            messageContainer.className = 'message-container';

            var messageElement = document.createElement('div');
            messageElement.className = 'message';
            messageElement.innerText = message;

            var senderElement = document.createElement('div');
            senderElement.className = 'message-sender';
            senderElement.innerText = sender;

            var timeElement = document.createElement('div');
            timeElement.className = 'message-time';
            timeElement.innerText = formatTime(time);

            messageContainer.appendChild(senderElement);
            messageContainer.appendChild(messageElement);
            messageContainer.appendChild(timeElement);

            chatContent.appendChild(messageContainer);

            // 滾動至最新消息處
            chatContent.scrollTop = chatContent.scrollHeight;
        }
    </script>
</body>

</html>
