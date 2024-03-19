package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.example.dao.FriendDAO;
import com.example.dao.UserDAO;
import com.example.demo.MySQLDemo;
import com.example.vo.FriendVO;

@Service
public class FriendListService {
    private final FriendDAO friendDAO;
    private final UserDAO userDAO;

    public FriendListService(FriendDAO friendDAO, UserDAO userDAO) {
        this.friendDAO = friendDAO;
        this.userDAO = userDAO;
    }

    public List<FriendVO> getUserFriendList(String userId) {
    	List<FriendVO> friendListVo = friendDAO.getUserFriendList(userId);
    	
    	for (FriendVO friend : friendListVo) {
    		String userName = userDAO.getName(userId);
    		String friendId = friend.getFriendId();
    		String friendName =  userDAO.getName(friendId);
    		String roomId = friend.getRoomId();
    		friend.setUserId(userId);
    		friend.setUserName(userName);
    		friend.setFriendId(friendId);
    		friend.setFriendName(friendName);
    		friend.setRoomId(roomId);
    	}
    	return friendListVo;
    }
}
