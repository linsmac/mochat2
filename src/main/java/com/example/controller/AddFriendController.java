package com.example.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.service.AddFriendService;
import com.example.service.FriendListService;
import com.example.vo.FriendVO;
import com.example.vo.UserVO;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class AddFriendController {
	
    @Autowired
    private FriendListService friendListService;
    
    @Autowired
    private AddFriendService addFriendService;
	
    @GetMapping("/AddFriend")
    public String AddFriend(HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam("userId") String userId,@RequestParam("userName") String userName)throws ServletException, IOException{
    	HttpSession session = request.getSession();
    	request.setAttribute("userId", userId);
    	request.setAttribute("userName", userName);
        session.getServletContext().getRequestDispatcher("/WEB-INF/views/AddFriend.jsp").forward(request, response);
        return null;
    }
    
    @PostMapping("/SearchFriend")
    public ResponseEntity<String> SearchFriend(HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam("searchInput") String searchInput)throws ServletException, IOException{
    	List<UserVO> searchResults = addFriendService.SearchFriend(searchInput);
        ObjectMapper objectMapper = new ObjectMapper();
        String searchResultsJson = objectMapper.writeValueAsString(searchResults);
        return ResponseEntity.ok().body(searchResultsJson);
    }
    
    @PostMapping("/getUserFriendList")
    public ResponseEntity<List<FriendVO>> getUserFriendList(HttpServletRequest request, @RequestBody Map<String, String> requestBody) {
        String userId = requestBody.get("userId");
        List<FriendVO> friendList = friendListService.getUserFriendList(userId);
        return ResponseEntity.ok().body(friendList);
    }
    
    @PostMapping("/AddNewFriend")
    public ResponseEntity<String> AddNewFriend(HttpServletRequest request, HttpServletResponse response, Model model, @RequestBody Map<String, String> requestBody)throws ServletException, IOException{
        String userId = requestBody.get("userId");
        String friendId = requestBody.get("friendId");
    	String respMsg = addFriendService.AddNewFriend(userId, friendId);

        return ResponseEntity.ok().body(respMsg);
    }
}
