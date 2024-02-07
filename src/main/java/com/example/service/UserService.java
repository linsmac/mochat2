package com.example.service;

import org.springframework.stereotype.Service;

import com.example.demo.MySQLDemo;
import com.example.vo.UserVO;

@Service
public class UserService {

    private final MySQLDemo mySQLDemo;

    public UserService(MySQLDemo mySQLDemo) {
        this.mySQLDemo = mySQLDemo;
    }

    public UserVO validateUser(String account, String password) {
        UserVO userVO = new UserVO();
        userVO.setMessage(null);
        userVO = mySQLDemo.findUserByAccount(account);

        if (userVO.getPassWord() != null) {
        	if(!password.equals(userVO.getPassWord())) {
        		userVO.setMessage("密碼錯誤");
        	}
        }else {
        	userVO.setMessage("查無此帳號");
        }
        return userVO;
    }
    
}


