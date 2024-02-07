package com.example.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.service.ChatListService;
import com.example.service.UserService;
import com.example.vo.FriendVO;
import com.example.vo.LoginReqVo;
import com.example.vo.UserVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	
    @Autowired
    private UserService userService;
    @Autowired
    private ChatListService chatListService;


    public LoginController(UserService userService) {
        this.userService = userService;
    }
	

    @GetMapping("/Login")
    public String index(){
        System.out.println("Open Success");
        return "Login";
    }
    
    @PostMapping("/Login")
    public String login(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("loginForm") LoginReqVo loginForm, Model model) throws ServletException, IOException {
        HttpSession session = request.getSession();
    	UserVO userVo = userService.validateUser(loginForm.getAccount(), loginForm.getPassword());

        if (userVo.getMessage() == null) {
            List<FriendVO> chatList = chatListService.getUserChatList(userVo.getUserId());
            ObjectMapper objectMapper = new ObjectMapper();
            String chatListJson = objectMapper.writeValueAsString(chatList);

            request.setAttribute("chatListJson", chatListJson);
            session.getServletContext().getRequestDispatcher("/WEB-INF/views/ChatList.jsp").forward(request, response);
        } else {
            model.addAttribute("errorMessage", userVo.getMessage());
            return "Login";
        }
        return null;
    }
    

    
    @GetMapping("/ChatList")  // 添加此行
    public String showChatList(Model model) {
    	System.out.println("Open ChatList Success");
        return "ChatList";
    }
    
    @PostMapping("/ChatList")
    public String loginSuccess(@ModelAttribute("loginForm") LoginReqVo loginForm, Model model) {
        // 假設登入成功後，重定向到 ChatList.jsp
        return "redirect:/ChatList";
    }
    
    @GetMapping("/FriendList")
    public String showFriendList(Model model){
    	System.out.println("Open FriendList Success");
        return "FriendList";
    }

}
