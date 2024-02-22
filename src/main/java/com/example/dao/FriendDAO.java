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
import com.example.vo.FriendVO;

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
    
    public List<FriendVO> getRoomId(String userId) {
        Connection conn = null;
        Statement stmt = null;
        List<FriendVO> roomIdList = new ArrayList<>();

        try {
            conn = dbUtil.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM friend WHERE user_id = '" + userId + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
            	FriendVO friendVo = new FriendVO();
            	friendVo.setRoomId(rs.getString("room_id"));
            	friendVo.setUserId(rs.getString("user_Id"));
            	friendVo.setFriendId(rs.getString("friend_id"));
            	roomIdList.add(friendVo);
            }
            rs.close();
            dbUtil.closeConnection(conn);
            System.out.println("Connect End");
        } catch (SQLException | ClassNotFoundException se) {
            se.printStackTrace();
        } finally {
            dbUtil.closeConnection(conn);
        }
        return roomIdList;
    }
}