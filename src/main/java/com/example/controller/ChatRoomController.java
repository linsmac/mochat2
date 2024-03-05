package com.example.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.service.ChatRoomService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ChatRoomController {

	@Autowired
	private ChatRoomService chatRoomService;

	@GetMapping("/openChatRoom")
	public ResponseEntity<ObjectNode> openChatRoom(HttpServletRequest request, HttpServletResponse response,Model model, @RequestParam String roomId, @RequestParam String userId, @RequestParam String userName, @RequestParam String friendId, @RequestParam String friendName) throws ServletException, IOException {

		String chatContent = "Welcome to the chat room with " + friendName;
		ObjectNode jsonResponse = new ObjectMapper().createObjectNode();
		ObjectNode chatHistory = chatRoomService.getHistoryText(roomId);

		jsonResponse.put("roomId", roomId);
		jsonResponse.put("userId", userId);
		jsonResponse.put("userName", userName);
		jsonResponse.put("friendId", friendId);
		jsonResponse.put("friendName", friendName);
		jsonResponse.put("content", chatContent);
		jsonResponse.set("text", chatHistory);

		// 將聊天內容轉為 JSON 格式返回給前端
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "application/json").body(jsonResponse);
	}

//	@PostMapping("/sendMessage")
	@RequestMapping(value="/sendMessage", method=RequestMethod.POST)
	@ResponseBody
	public String updateOrInsertText(HttpServletRequest request, HttpServletResponse response,@RequestBody JsonNode requestBody) throws JsonMappingException, JsonProcessingException {
		String reqMessage = chatRoomService.updateOrInsertText(requestBody);

		return reqMessage;
	}
}