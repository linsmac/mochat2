package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.vo.LoginReqVo;

@Controller
public class LoginController {

    @GetMapping("/Login")
    public String index(){
        System.out.print("Open Success");
        return "Login";
    }
    
    @PostMapping("/Login") 
    public String processLogin(@ModelAttribute("loginForm") LoginReqVo loginForm, Model model) {
        // 假設登入驗證等相關邏輯
        if ("123".equals(loginForm.getAccount()) && "123".equals(loginForm.getPassword())) {
            // 認證成功，重定向到 ChatList.jsp
            return "redirect:/ChatList";
        } else {
            // 認證失敗，返回登入頁面
            model.addAttribute("error", "帳號或密碼錯誤");
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
