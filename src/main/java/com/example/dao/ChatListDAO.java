package com.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.vo.FriendVO;

@Component
public class ChatListDAO {
	
    @Value("${spring.datasource.url}")
    private  String dbUrl;

    @Value("${spring.datasource.username}")
    private  String dbUsername;

    @Value("${spring.datasource.password}")
    private  String dbPassword;
    
    @Value("${spring.datasource.driver}")
    private  String jdbcDriver;
    
	public List<FriendVO> getUserChatList(String userId){
        Connection conn = null;
        Statement stmt = null;
        List<FriendVO> friendList = new ArrayList<>();
        try {
            Class.forName(jdbcDriver);
            System.out.println("Connect to DB...");
            conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);

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
