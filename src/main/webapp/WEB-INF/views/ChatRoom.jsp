<!-- 聊天室頁面 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<style>

</style>

<head>
    <meta charset="UTF-8">
    <link type="text/css" rel="stylesheet" href="/css/chatRoomStyle.css">
    <title>Chat Room</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
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
     
    
 /*   $(document).ready(function(){
    	
    });*/

	    // 假設點擊了聊天室列表中的某個聊天室，將聊天對象名稱傳遞給 setChatHeader 函數
	    var chatData = JSON.parse(sessionStorage.getItem('chatData'));
	    setChatHeader(chatData.friendName);
	
	    // 假設從後端取得了過去的歷史消息數據，將它們插入到 .chat-content 中
	    var chatContent = document.getElementById('chatContent');
	   
	    var historyMessages =  chatData.text.chatRooms[0];
	    if(historyMessages){
	    	  historyMessages.forEach(function (msg) {
	  	        appendMessage(msg.name, msg.message, msg.timestamp);
	  	    });
		}
	  
	
	    function appendMessage(name, message, timestamp) {
	        var messageContainer = document.createElement('div');
	        messageContainer.className = 'message-container';
	
	        var messageElement = document.createElement('div');
	        messageElement.className = 'message';
	        messageElement.innerText = message;
	
	        var nameElement = document.createElement('div');
	        nameElement.className = 'message-name';
	        nameElement.innerText = name;
	
	        var timestampElement = document.createElement('div');
	        timestampElement.className = 'message-timestamp';
	        timestampElement.innerText = formatTime(new Date(timestamp));
	
	        messageContainer.appendChild(nameElement);
	        messageContainer.appendChild(messageElement);
	        messageContainer.appendChild(timestampElement);
	
	        chatContent.appendChild(messageContainer);
	
	        // 滾動至最新消息處
	        chatContent.scrollTop = chatContent.scrollHeight;
	        
/*	        setInterval(function () {
	            reloadChatContent();
	        }, 120000);
	        */
	    }
    
        function sendMessage() {
        	var chatData = JSON.parse(sessionStorage.getItem('chatData'));
            var messageInput = document.getElementById('messageInput');
            var message = messageInput.value;
            if (message.trim() !== '') {
            	
                var chatContent = document.getElementById('chatContent');
                var messageContainer = document.createElement('div');
                messageContainer.className = 'message-container';

                var messageElement = document.createElement('div');
                messageElement.className = 'message';
                messageElement.innerText = message;

                var nameElement = document.createElement('div');
                nameElement.className = 'message-name';
                nameElement.innerText = chatData.userName;

                var timestampElement = document.createElement('div');
                timestampElement.className = 'message-timestamp';
                var currentTime = new Date();
                timestampElement.innerText = formatTime(currentTime);

                messageContainer.appendChild(nameElement);
                messageContainer.appendChild(messageElement);

                chatContent.appendChild(messageContainer);
                
                // 清空輸入框
                messageInput.value = '';

                // 滾動至最新消息處
                chatContent.scrollTop = chatContent.scrollHeight;
                
                if (message !== '') {

                    $.ajax({
                        url: '/sendMessage',
                        type: 'POST',
                        contentType: 'application/json',
                        data: JSON.stringify({
                            roomId: chatData.roomId,
                            userId: chatData.userId,
                            userName: chatData.userName,
                            friendId: chatData.friendId,
                            friendName: chatData.friendName,
                            historyText: JSON.stringify(chatData.text.chatRooms[0]),
                            text: JSON.stringify([{ name: chatData.userName, message: message, timestamp:Math.floor(Date.now() / 1000) }])
                        }),
                        success: function (response) {
                            if (response === '00') {
                                var timestampElement = document.createElement('div');
                                timestampElement.className = 'message-timestamp';
                                timestampElement.innerText = formatTime(new Date());
                                messageContainer.appendChild(timestampElement);
                                
                                reloadChatContent();
                                //window.location.reload();
                            } else {
                                console.error('Failed to send message');
                                var errorElement = document.createElement('div');
                                errorElement.className = 'message-timestamp';
                                errorElement.innerText = '傳送失敗';
                                messageContainer.appendChild(errorElement);
                            }
                        },
	                    error: function (xhr, status, error) {
	                        console.error('Ajax request failed:', error);
	                    }
                    });
                }
                
            }
        }

        function closeChat() {
            // 在此處添加關閉聊天室的邏輯，可以使用 window.location.href 或其他方式
            var chatData = JSON.parse(sessionStorage.getItem('chatData'));
            var userId = ${userId};
            var userName = chatData.userName;
            window.location.href = '/ChatList?userId=' + userId+ '&userName=' + userName;
        }
        
        window.onbeforeunload = function() {
            if (window.location.href.indexOf("ChatRoom") > -1) {
                var checkFromChatRoom = true; // 添加一個檢查參數
                sessionStorage.setItem('checkFromChatRoom', checkFromChatRoom);
            }
        };

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
        
        function reloadChatContent() {
            var chatData = JSON.parse(sessionStorage.getItem('chatData'));
            fetch('/openChatRoom?roomId=' + chatData.roomId + '&userId=' + chatData.userId + '&userName=' + chatData.userName + '&friendId=' + chatData.friendId + '&friendName=' + chatData.friendName)
                .then(response => response.json())
                .then(data => {
                    // 更新 sessionStorage 中的聊天室数据
                    sessionStorage.setItem('chatData', JSON.stringify(data));
                    // 清空聊天内容
                    document.getElementById('chatContent').innerHTML = '';
                    // 根据新的数据重新渲染聊天内容
                    var historyMessages = data.text.chatRooms[0];
                    historyMessages.forEach(function (msg) {
                        appendMessage(msg.name, msg.message, msg.timestamp);
                    });
                })
                .catch(error => console.error('Error reloading chat content:', error));
//            	chatData = sessionStorage.getItem('chatData');
        }



    </script>
</body>

</html>
