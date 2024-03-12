package com.example.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.vo.LoginReqVo;
import com.example.vo.RegisterReqVO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class RegisterController {
    
    @GetMapping("/Register")
    public String showRegisterPage() {
        return "Register";
    }
    
    @GetMapping("/CancelRegister")
    public String cancel() {
        return "Login";
    }
    
    @PostMapping("/Register")
    public ResponseEntity<String> registerUser(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("registerForm") RegisterReqVO registerForm, Model model) throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	System.out.print(registerForm.getAccount());
        return ResponseEntity.ok().body("success");
    }
}

