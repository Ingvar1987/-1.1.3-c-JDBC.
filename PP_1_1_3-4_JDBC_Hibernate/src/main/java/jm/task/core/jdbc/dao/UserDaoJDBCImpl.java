package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.util.Util;
import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        Util until = new Util();
        String command = "CREATE TABLE IF NOT EXISTS Users (id SERIAL PRIMARY KEY,\n" +
                "                          name VARCHAR(45) NOT NULL,\n" +
                "                  lastName VARCHAR(45) NOT NULL,\n" +
                "                      age SMALLINT NOT NULL)";

        try (Connection conn = until.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(command);
            System.out.println("Users таблица создана в базе данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void dropUsersTable() {
        String command = "DROP TABLE IF EXISTS Users;";
        Util until = new Util();

        try (Connection conn = until.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(command);
            System.out.println("Users таблица удалена из базы данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        Util until = new Util();
        String command = "INSERT INTO Users (name, lastName, age) VALUES ('" + name + "', '" + lastName + "', " + age + ")";

        try (Connection connection = until.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(command);
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void removeUserById(long id) {
        Util util = new Util();
        String command = "DELETE FROM Users WHERE id = ?";
        try (Connection conn = util.getConnection(); PreparedStatement pstmt = conn.prepareStatement(command)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
            System.out.println("User с id – " + id + " удален ");
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public List<User> getAllUsers() {
        Util util = new Util();
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        try (Connection connection = util.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);

            }
            System.out.println("список всех пользователей таблицы Users получены!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        Util util = new Util();
        String command = "TRUNCATE TABLE Users";

        try (Connection conn = util.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(command);
            System.out.println("таблица User – очищенна");
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}