package com.example.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.conn.DBUtil;
import com.example.vo.ChatRoomVO;
import com.example.vo.FriendVO;
import com.example.vo.RegisterReqVO;
import com.example.vo.UserVO;
import com.fasterxml.jackson.databind.node.ObjectNode;

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
    
    public String getName(String id) {
        Connection conn = null;
        Statement stmt = null;
        String userName = null;

        try {
            conn = dbUtil.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT name FROM user WHERE user_id = '" + id + "'";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                userName = rs.getString("name");
            }
            rs.close();
        } catch (SQLException | ClassNotFoundException se) {
            se.printStackTrace();
        } finally {
            dbUtil.closeConnection(conn);
        }
        return userName;
    }
    
    public RegisterReqVO registerAccount(String Name, String account, String password) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        RegisterReqVO registerReqVO = new RegisterReqVO();

        try {
            conn = dbUtil.getConnection();
            stmt = conn.createStatement();

            // 檢查帳戶是否已被使用
            String checkQuery = "SELECT * FROM user WHERE account = '" + account + "'";
            rs = stmt.executeQuery(checkQuery);

            if (!rs.next()) {
            	String insertQuery = "INSERT INTO user (user_id, name, account, password, channel) " +
                        "SELECT CONCAT(IFNULL(MAX(CAST(user_id AS UNSIGNED)), 0) + 1), " +
                        "'" + Name + "', '" + account + "', '" + password + "', " +
                        "CONCAT(IFNULL(MAX(CAST(channel AS UNSIGNED)), 0) + 1) FROM user";                stmt.executeUpdate(insertQuery);
                //註冊成功！
                registerReqVO.setMessage("00");
            } else {
            	//註冊失敗，該帳戶已被使用
            	registerReqVO.setMessage("註冊失敗，該帳戶已被使用");
            }
            rs.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            //DB連線失敗
            registerReqVO.setMessage("連線失敗");
		} finally {
			dbUtil.closeConnection(conn);
        }

        return registerReqVO;
    }
    
    public List<UserVO> searchUser(String searchInput) {
        Connection conn = null;
        Statement stmt = null;
        List<UserVO> searchList = new ArrayList<>();

        try {
            conn = dbUtil.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM user WHERE account = '" + searchInput + "' OR name = '" + searchInput + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
            	UserVO userVO = new UserVO();
            	userVO.setAccount(rs.getString("account"));
            	userVO.setName(rs.getString("name"));
            	userVO.setUserId(rs.getString("user_id"));

                searchList.add(userVO);
            }
            rs.close();
        } catch (SQLException | ClassNotFoundException se) {
            se.printStackTrace();
        } finally {
            dbUtil.closeConnection(conn);
        }
        return searchList;
    }
}