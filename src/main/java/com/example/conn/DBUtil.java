package com.example.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DBUtil {
    private String dbUrl;
    private String dbUsername;
    private String dbPassword;
    private String jdbcDriver;

    @Value("${spring.datasource.url}")
    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    @Value("${spring.datasource.username}")
    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    @Value("${spring.datasource.password}")
    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }
    
    @Value("${spring.datasource.driver}")
    public void setJdbcDriver(String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(jdbcDriver);
        System.out.println("Connect to DB...");
        return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
    }

    public void closeConnection(Connection conn) {
        try {
            if (conn != null) conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}