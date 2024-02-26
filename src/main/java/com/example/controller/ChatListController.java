package com.example.controller;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

//聊天列表控制器
@Controller
public class ChatListController {
	
    @GetMapping("/ChatRoom")
    public String showChatRoom(HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam("userId") String userId)throws ServletException, IOException{
    	HttpSession session = request.getSession();
    	request.setAttribute("userId", userId);
        session.getServletContext().getRequestDispatcher("/WEB-INF/views/ChatRoom.jsp").forward(request, response);
        return null;
    }

}
