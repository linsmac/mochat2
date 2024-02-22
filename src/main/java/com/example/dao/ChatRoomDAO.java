package com.example.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.conn.DBUtil;
import com.example.vo.ChatRoomVO;
import com.example.vo.FriendVO;

@Component
public class ChatRoomDAO {

    @Autowired
    private DBUtil dbUtil;
    @Autowired
    private FriendDAO friendDAO;
    
    public List<ChatRoomVO> getUserChatList(String roomId) {
        Connection conn = null;
        Statement stmt = null;
        List<ChatRoomVO> chatList = new ArrayList<>();
        
            try {
                conn = dbUtil.getConnection();
                stmt = conn.createStatement();
            	String sql;
	            sql = "SELECT * FROM chatroom WHERE room_id = '" + roomId + "'";
	            ResultSet rs = stmt.executeQuery(sql);

	            while (rs.next()) {
	                ChatRoomVO chatRoomVo = new ChatRoomVO();
	                chatRoomVo.setRoomId(rs.getInt("room_id"));
	                chatRoomVo.setText(rs.getString("text"));
	
	                chatList.add(chatRoomVo);
	            }
	
            rs.close();        
            dbUtil.closeConnection(conn);
            System.out.println("Connect End");
        } catch (SQLException | ClassNotFoundException se) {
            se.printStackTrace();
        } finally {
            dbUtil.closeConnection(conn);
        }
       

        return chatList;
    }

}