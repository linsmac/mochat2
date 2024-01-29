package com.example.demo;

import java.sql.*;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.vo.UserVO;

@Component
public class MySQLDemo {

// MySQL 8.0 以下版本
//static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
//static final String DB_URL = "jdbc:mysql://localhost:3306/moDB";

// MySQL 8.0 以上版本
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/mochattest?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

	static final String USER = "root";
	static final String PASS = "123456";

    public UserVO findUserByAccount(String account) {
        Connection conn = null;
        Statement stmt = null;
        UserVO userVo = new UserVO();
        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("Connect to DB...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM user WHERE account = '" + account + "'";
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

        return userVo;
    }
}