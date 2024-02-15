package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dao.ChatListDAO;
import com.example.dao.UserDAO;
import com.example.demo.MySQLDemo;
import com.example.vo.ChatRoomVO;
import com.example.vo.FriendVO;

@Service
public class ChatListService {
  //  private final MySQLDemo mySQLDemo;
    private final ChatListDAO chatListDAO;
    private final UserDAO userDAO;
    
	public ChatListService(UserDAO userDAO, ChatListDAO chatListDAO) {
	    this.userDAO = userDAO;
	    this.chatListDAO = chatListDAO;
	}
	
	public List<ChatRoomVO> getUserChatList(String userId) {
	        return chatListDAO.getUserChatList(userId);
	}

}
