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

import com.example.vo.ChatRoomVO;
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
    
	public List<ChatRoomVO> getUserChatList(String userId){
        Connection conn = null;
        Statement stmt = null;
        List<ChatRoomVO> chatList = new ArrayList<>();
        try {
            Class.forName(jdbcDriver);
            System.out.println("Connect to DB...");
            conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);

            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM chatroom WHERE user1_id = '" + userId + "' OR user2_id = '"+ userId +"'";
            ResultSet rs = stmt.executeQuery(sql);
                       
            while (rs.next()) {
                ChatRoomVO chatRoomVo = new ChatRoomVO();
                chatRoomVo.setRoomId(rs.getInt("room_id"));
                chatRoomVo.setUser1Id(rs.getString("user1_id"));
                chatRoomVo.setUser2Id(rs.getString("user2_id"));
                chatRoomVo.setText(rs.getString("text"));
                
                String friendId = rs.getString("user1_id").equals(userId) ? rs.getString("user2_id") : rs.getString("user1_id");
                String friendName = getFriendNameFromDatabase(friendId); // 从数据库中获取对方的name
                chatRoomVo.setFriendName(friendName);
                
                chatList.add(chatRoomVo);
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

        return chatList;
    }
	
	private String getFriendNameFromDatabase(String friendId) {
	    Connection conn = null;
	    Statement stmt = null;
	    String friendName = null;
	    try {
	        Class.forName(jdbcDriver);
	        conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
	        stmt = conn.createStatement();
	        String sql = "SELECT name FROM user WHERE user_id = '" + friendId + "'";
	        ResultSet rs = stmt.executeQuery(sql);
	        if (rs.next()) {
	            friendName = rs.getString("name");
	        }
	        rs.close();
	        stmt.close();
	        conn.close();
	    } catch (SQLException | ClassNotFoundException se) {
	        se.printStackTrace();
	    } finally {
	        try {
	            if (stmt != null) stmt.close();
	        } catch (SQLException se) {
	            se.printStackTrace();
	        }
	        try {
	            if (conn != null) conn.close();
	        } catch (SQLException se) {
	            se.printStackTrace();
	        }
	    }
	    return friendName;
	}

}
