package com.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.vo.UserVO;
import com.example.vo.FriendVO;

@Component
public class ChatListDAO {
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/mochattest?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

	static final String USER = "root";
	static final String PASS = "123456";

	public List<FriendVO> getUserChatList(String userId){
        Connection conn = null;
        Statement stmt = null;
        List<FriendVO> friendList = new ArrayList<>();
        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("Connect to DB...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM friend WHERE user_id = '" + userId + "'";
            ResultSet rs = stmt.executeQuery(sql);
//            List<FriendVO> friendList = new ArrayList<FriendVO>();
                       
            while (rs.next()) {
                FriendVO friendVo = new FriendVO();
                friendVo.setFriendShipId(rs.getInt("friendship_id"));
                friendVo.setFriendId(rs.getString("friend_id"));
                friendVo.setFriendName(rs.getString("friend_name"));
                friendList.add(friendVo);
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
            System.out.println("Connect End");
        } catch (SQLException | ClassNotFoundException se) {
            se.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return friendList;
    }
}
