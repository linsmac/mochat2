package com.example.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class ChatRoomController {

    @GetMapping("/ChatRoom")
    public String showChatRoom(HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam("userId") String userId)throws ServletException, IOException{
    	HttpSession session = request.getSession();
        session.getServletContext().getRequestDispatcher("/WEB-INF/views/ChatRoom.jsp").forward(request, response);
        return null;
    }
    
    
}