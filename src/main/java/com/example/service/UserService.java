package com.example.service;

import org.springframework.stereotype.Service;

import com.example.dao.UserDAO;
import com.example.vo.UserVO;

@Service
public class UserService {

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UserVO validateUser(String account, String password) {
        UserVO userVO = new UserVO();
        userVO.setMessage(null);
        userVO = userDAO.findUserByAccount(account);

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


