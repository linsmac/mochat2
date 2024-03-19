package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dao.FriendDAO;
import com.example.dao.UserDAO;
import com.example.vo.UserVO;

@Service
public class AddFriendService {
	
	private final UserDAO userDAO;
	private final FriendDAO friendDAO;
	
    public AddFriendService(UserDAO userDAO, FriendDAO friendDAO) {
        this.userDAO = userDAO;
        this.friendDAO = friendDAO;
    }
	
    public List<UserVO> SearchFriend(String searchInput) {
    	List<UserVO> searchResults = userDAO.searchUser(searchInput);
    	return searchResults;
    }
    
    public String AddNewFriend(String userId, String friendId) {
    	String respMsg = friendDAO.addNewFriend(userId, friendId);
    	return respMsg;
    }

}
