package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String dbName = "newbd1_23";
    private static final String dbUser = "root";
    private static final String dbPass = "rootuser";
    private static final String dbUrl = "jdbc:mysql://localhost:3306/" + dbName;

    // реализуйте настройку соеденения с БД

    public static Connection getConnect() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUser, dbPass);
    }
}
