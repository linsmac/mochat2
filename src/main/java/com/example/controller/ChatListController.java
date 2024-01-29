package com.example.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//聊天列表控制器
@Controller
public class ChatListController {

 @GetMapping("/openChatRoom")
 public ResponseEntity<String> openChatRoom(@RequestParam String username) {
     // 根據聊天室名稱（username）獲取相應的聊天內容
     // 在實際應用中，你需要通過服務層和資料庫進行相應的操作
     String chatContent = "Welcome to the chat room with " + username;

     // 將聊天內容轉為 JSON 格式返回給前端
     return ResponseEntity.ok()
             .header(HttpHeaders.CONTENT_TYPE, "application/json")
             .body("{\"username\": \"" + username + "\", \"content\": \"" + chatContent + "\"}");
 }
}
