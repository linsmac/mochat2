package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dao.ChatRoomDAO;
import com.example.dao.FriendDAO;
import com.example.dao.UserDAO;
import com.example.demo.MySQLDemo;
import com.example.vo.ChatRoomVO;
import com.example.vo.FriendVO;

@Service
public class ChatListService {
  //  private final MySQLDemo mySQLDemo;
    private final ChatRoomDAO chatRoomDAO;
    private final UserDAO userDAO;
    private final FriendDAO friendDAO;
    
	public ChatListService(UserDAO userDAO, ChatRoomDAO chatRoomDAO, FriendDAO friendDAO) {
	    this.userDAO = userDAO;
	    this.chatRoomDAO = chatRoomDAO;
	    this.friendDAO = friendDAO;
	}
	
	public List<ChatRoomVO> getUserChatList(String userId) {
		List<FriendVO> roomIdList = friendDAO.getUserFriendList(userId);
		List<ChatRoomVO> chatListVo = new ArrayList<>();
		
		for (FriendVO RoomId : roomIdList) {
		    List<ChatRoomVO> chatRooms = chatRoomDAO.getUserChatList(RoomId.getRoomId());
	        for (ChatRoomVO room : chatRooms) {
	            String friendId = RoomId.getFriendId();
	            String friendName = userDAO.getName(friendId);
	            room.setUserId(userId);
	            room.setFriendId(friendId);
	            room.setFriendName(friendName);
	            chatListVo.add(room);
	        }	
		}
	return chatListVo;
	}
}
