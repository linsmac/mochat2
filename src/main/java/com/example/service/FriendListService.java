package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.example.dao.FriendDAO;
import com.example.demo.MySQLDemo;
import com.example.vo.FriendVO;

@Service
public class FriendListService {
    private final MySQLDemo mySQLDemo;
    private final FriendDAO friendDAO;

    public FriendListService(MySQLDemo mySQLDemo, FriendDAO friendDAO) {
        this.mySQLDemo = mySQLDemo;
        this.friendDAO = friendDAO;
    }

    public List<FriendVO> getUserFriendList(String userId) {
            return friendDAO.getUserFriendList(userId);
    }

}
