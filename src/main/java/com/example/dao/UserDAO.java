package com.example.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.conn.DBUtil;
import com.example.vo.UserVO;

@Component
public class UserDAO {

    @Autowired
    private DBUtil dbUtil;

    public UserVO findUserByAccount(String account) {
        Connection conn = null;
        Statement stmt = null;
        UserVO userVo = new UserVO();
        try {
            conn = dbUtil.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM user WHERE account = '" + account + "'";
            ResultSet rs = stmt.executeQuery(sql);
            
            String id = null;
            String fetchedAccount = null;
            String password = null;
                       
            if (rs.next()) {
                id = rs.getString("user_id");
                fetchedAccount = rs.getString("account");
                password = rs.getString("password");
                userVo.setUserId(id);
                userVo.setAccount(fetchedAccount);
                userVo.setPassWord(password);
            }
            
            rs.close();
            dbUtil.closeConnection(conn);
            System.out.println("Connect End");
        } catch (SQLException | ClassNotFoundException se) {
            se.printStackTrace();
        } finally {
            dbUtil.closeConnection(conn);
        }

        return userVo;
    }
}