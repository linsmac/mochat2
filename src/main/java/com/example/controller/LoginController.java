package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.MySQLDemo;
import com.example.service.UserService;
import com.example.vo.LoginReqVo;
import com.example.vo.UserVO;

@Controller
public class LoginController {
	
    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }
	

    @GetMapping("/Login")
    public String index(){
        System.out.println("Open Success");
        return "Login";
    }
    
    @PostMapping("/Login")
    public String login(@ModelAttribute("loginForm") LoginReqVo loginForm) {
        if (userService.validateUser(loginForm.getAccount(), loginForm.getPassword())) {
            return "redirect:/ChatList";
        } else {
            System.out.println("account or password incorrect");
            return "Login";
        }
    }

    
    @GetMapping("/ChatList")  // 添加此行
    public String showChatList(Model model) {
        // 在這裡處理顯示ChatList的邏輯
        return "ChatList";
    }
    
    @PostMapping("/ChatList")
    public String loginSuccess(@ModelAttribute("loginForm") LoginReqVo loginForm, Model model) {
        // 假設登入成功後，重定向到 ChatList.jsp
        return "redirect:/ChatList";
    }

}
