package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.MySQLDemo;
import com.example.vo.UserVO;

@Service
public class UserService {

    private final MySQLDemo mySQLDemo;

    @Autowired
    public UserService(MySQLDemo mySQLDemo) {
        this.mySQLDemo = mySQLDemo;
    }

    public boolean validateUser(String account, String password) {
        UserVO userVO = mySQLDemo.findUserByAccount(account);

        if (userVO != null) {
        	return password.equals(userVO.getPassWord());    		
        }

        return false;
    }
}


