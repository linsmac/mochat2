package com.example.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.service.RegisterService;
import com.example.vo.RegisterReqVO;

import jakarta.servlet.ServletException;

@Controller
public class RegisterController {
    @Autowired
    private RegisterService registerService;
    
    @GetMapping("/Register")
    public String showRegisterPage() {
        return "Register";
    }
    
    @PostMapping("/Register")
    public ResponseEntity<String> RegisterAaccount(@ModelAttribute("registerForm") RegisterReqVO registerForm, Model model) throws ServletException, IOException {
    	RegisterReqVO registerReqMsg = registerService.RegisterAaccount(registerForm.getName(), registerForm.getAccount(), registerForm.getPassword());
        return ResponseEntity.ok().body(registerReqMsg.getMessage());
    }
}

