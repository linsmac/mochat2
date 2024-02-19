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

@Component
public class ChatRoomDAO {

    @Autowired
    private DBUtil dbUtil;

    public List<ChatRoomVO> getUserChatList(String userId) {
        Connection conn = null;
        Statement stmt = null;
        List<ChatRoomVO> chatList = new ArrayList<>();

        try {
            conn = dbUtil.getConnection();
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM chatroom WHERE user1_id = '" + userId + "' OR user2_id = '" + userId + "'";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                ChatRoomVO chatRoomVo = new ChatRoomVO();
                chatRoomVo.setRoomId(rs.getInt("room_id"));
                chatRoomVo.setUser1Id(rs.getString("user1_id"));
                chatRoomVo.setUser2Id(rs.getString("user2_id"));
                chatRoomVo.setText(rs.getString("text"));

                String friendId = rs.getString("user1_id").equals(userId) ? rs.getString("user2_id") : rs.getString("user1_id");
                String friendName = getFriendNameFromDatabase(friendId);
                chatRoomVo.setFriendName(friendName);

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

    private String getFriendNameFromDatabase(String friendId) {
        Connection conn = null;
        Statement stmt = null;
        String friendName = null;

        try {
            conn = dbUtil.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT name FROM user WHERE user_id = '" + friendId + "'";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                friendName = rs.getString("name");
            }
            rs.close();
        } catch (SQLException | ClassNotFoundException se) {
            se.printStackTrace();
        } finally {
            dbUtil.closeConnection(conn);
        }
        return friendName;
    }
}