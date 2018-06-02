package io.sweetfab.sweetideapi.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Nayeon Kim
 * @version 1.0
 * @since 2018-05-26
 */

public class Database {

    @Autowired
    private static JdbcTemplate jdbc;
    private static Connection connection;

    static {
        try {
            connection = jdbc.getDataSource().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static PreparedStatement build(String sql, Object... args) {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(sql);
            int count = 1;
            for (Object o : args) {
                stmt.setObject(count++, o);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stmt;
    }

    public static int executeUpdate(String sql, Object... args) {
        PreparedStatement stmt = build(sql, args);
        try {
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static ResultSet executeQuery(String sql, Object... args) {
        PreparedStatement stmt = build(sql, args);
        try {
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
