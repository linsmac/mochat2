package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatRoomController {

    @GetMapping("/ChatRoom")
    public String showChatRoom() {
        return "ChatRoom"; // 返回聊天室頁面的視圖名稱
    }
}