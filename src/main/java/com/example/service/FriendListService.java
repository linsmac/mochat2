package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.example.dao.FriendListDAO;
import com.example.demo.MySQLDemo;
import com.example.vo.FriendVO;

@Service
public class FriendListService {
    private final MySQLDemo mySQLDemo;
    private final FriendListDAO friendListDAO;

    public FriendListService(MySQLDemo mySQLDemo, FriendListDAO friendListDAO) {
        this.mySQLDemo = mySQLDemo;
        this.friendListDAO = friendListDAO;
    }

    public List<FriendVO> getUserFriendList(String userId) {
            return friendListDAO.getUserFriendList(userId);
    }

}
