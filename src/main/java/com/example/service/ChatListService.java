package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.example.dao.ChatListDAO;
import com.example.demo.MySQLDemo;
import com.example.vo.FriendVO;

@Service
public class ChatListService {
    private final MySQLDemo mySQLDemo;
    private final ChatListDAO chatListDAO;

    public ChatListService(MySQLDemo mySQLDemo, ChatListDAO chatListDAO) {
        this.mySQLDemo = mySQLDemo;
        this.chatListDAO = chatListDAO;
    }

    public List<FriendVO> getUserChatList(String userId) {
            return chatListDAO.getUserChatList(userId);
    }

}
