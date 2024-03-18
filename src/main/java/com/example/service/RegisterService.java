package com.example.service;

import org.springframework.stereotype.Service;

import com.example.dao.UserDAO;
import com.example.vo.RegisterReqVO;
import com.example.vo.UserVO;

@Service
public class RegisterService {
	
    private final UserDAO userDAO;

    public RegisterService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    
    public RegisterReqVO RegisterAaccount(String Name, String account, String password) {
    	RegisterReqVO registerReqMsg = new RegisterReqVO();
    	registerReqMsg = userDAO.registerAccount(Name, account, password);

        return registerReqMsg;
    }

}
