package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final String createUsersTable = """
            CREATE TABLE IF NOT EXISTS users (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(100) NOT NULL,
                lastName VARCHAR(100) NOT NULL,
                age TINYINT(255) NOT NULL        
            )
            """;
    private static final String dropUsersTable = "DROP TABLE IF EXISTS users";
    private static final String delUser = """
                DELETE FROM users
                WHERE id = ?
            """;
    private static final String insertUser = """
                INSERT INTO users (username, lastName, age)
                VALUES (?, ?, ?)
            """;
    private static final String allUsers = """
            SELECT username, lastName, age FROM users""";
    public static final String clearTable = "TRUNCATE TABLE users";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection con = Util.getConnect(); Statement stmt = con.createStatement()) {
            stmt.executeUpdate(createUsersTable);
            //System.out.println("Таблица users создана (если её не было).");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (Connection con = Util.getConnect(); Statement stmt = con.createStatement()) {
            stmt.executeUpdate(dropUsersTable);
            // System.out.println("Таблица users удалена (если она существовала).");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection con = Util.getConnect(); PreparedStatement prStm = con.prepareStatement(insertUser)) {
            prStm.setString(1, name);
            prStm.setString(2, lastName);
            prStm.setInt(3, age);
            System.out.println("User с именем - " + name + " добавлен в базу данных");
            int rows = prStm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void removeUserById(long id) {
        try (Connection con = Util.getConnect(); PreparedStatement prStm = con.prepareStatement(delUser)) {
            prStm.setInt(1, (int) id);
            // System.out.println("User c id - " + id + " удален.");
            int rows = prStm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection con = Util.getConnect(); PreparedStatement prStm = con.prepareStatement(allUsers)) {
            ResultSet rSet = prStm.executeQuery();
            while (rSet.next()) {
                User user = new User(
                        rSet.getString("username"),
                        rSet.getString("lastName"),
                        rSet.getByte("age")
                );
                users.add(user);
            }
            //System.out.println("Список users создан.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Connection con = Util.getConnect(); Statement stm = con.createStatement()) {
            stm.executeUpdate(clearTable);
            // System.out.println("Таблица users очищена.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
