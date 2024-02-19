package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dao.ChatRoomDAO;
import com.example.dao.UserDAO;
import com.example.demo.MySQLDemo;
import com.example.vo.ChatRoomVO;
import com.example.vo.FriendVO;

@Service
public class ChatListService {
  //  private final MySQLDemo mySQLDemo;
    private final ChatRoomDAO chatRoomDAO;
    private final UserDAO userDAO;
    
	public ChatListService(UserDAO userDAO, ChatRoomDAO chatRoomDAO) {
	    this.userDAO = userDAO;
	    this.chatRoomDAO = chatRoomDAO;
	}
	
	public List<ChatRoomVO> getUserChatList(String userId) {
	        return chatRoomDAO.getUserChatList(userId);
	}

}
