package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.conn.DBUtil;
import com.example.vo.FriendVO;
import com.example.vo.UserVO;

@Component
public class FriendDAO {
    
    @Autowired
    private DBUtil dbUtil;
    @Autowired
    private UserDAO userDao;

    public List<FriendVO> getUserFriendList(String userId) {
        Connection conn = null;
        Statement stmt = null;
        List<FriendVO> friendList = new ArrayList<>();

        try {
            conn = dbUtil.getConnection();
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM friend WHERE user_id = '" + userId + "'";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                FriendVO friendVo = new FriendVO();
            	friendVo.setRoomId(rs.getString("room_id"));
            	friendVo.setUserId(rs.getString("user_Id"));
                friendVo.setFriendShipId(rs.getInt("friendship_id"));
                friendVo.setFriendId(rs.getString("friend_id"));
                friendList.add(friendVo);
            }

            rs.close();
            dbUtil.closeConnection(conn);
            System.out.println("Connect End");
        } catch (SQLException | ClassNotFoundException se) {
            se.printStackTrace();
        } finally {
            dbUtil.closeConnection(conn);
        }

        return friendList;
    }
    
    public String addNewFriend(String userId, String friendId) {
        Connection conn = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        String respMsg = "01";

        try {
            conn = dbUtil.getConnection();
            conn.setAutoCommit(false);
            int maxFriendshipId = 0;
            int maxRoomId = 0;
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT MAX(friendship_id) AS max_id FROM friend";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                maxFriendshipId = rs.getInt("max_id");
            }
            rs.close();
            rs = stmt.executeQuery("SELECT MAX(room_id) AS max_id FROM friend");
            if (rs.next()) {
                maxRoomId = rs.getInt("max_id");
            }
            stmt.close();
            
            //插入新的好友列表
            sql = "INSERT INTO friend (friendship_id, user_id, friend_id, room_id) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            
            // 插入第一條：userId -> friendId
            pstmt.setInt(1, maxFriendshipId + 1);
            pstmt.setString(2, userId);
            pstmt.setString(3, friendId);
            pstmt.setInt(4, maxRoomId + 1);
            pstmt.executeUpdate();
            
            // 插入第二條：friendId -> userId
            pstmt.setInt(1, maxFriendshipId + 2);
            pstmt.setString(2, friendId);
            pstmt.setString(3, userId);
            pstmt.setInt(4, maxRoomId + 1); // 使用相同的 room_id
            pstmt.executeUpdate();
            
            conn.commit();
            
            respMsg = "00";
        } catch (SQLException | ClassNotFoundException se) {
            se.printStackTrace();
        } finally {
            dbUtil.closeConnection(conn);
        }
        return respMsg;
    }
    
}