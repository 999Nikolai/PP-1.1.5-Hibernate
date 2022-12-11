package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
   Connection connection = Util.open();


    public UserDaoJDBCImpl() {   //Реализация JDBC пользовательского Dao
    }
    public void createUsersTable() { //создать таблицу пользователей
        String table = """
                    CREATE TABLE IF NOT EXISTS users (
                    id BIGINT PRIMARY KEY AUTO_INCREMENT,
                    name_id VARCHAR(40),
                    lastname_id VARCHAR(40),
                    age_id TINYINT
                    )
                    """;
        try (PreparedStatement statement = connection.prepareStatement(table)) {
            statement.execute(table);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void dropUsersTable() {  //удалить таблицу пользователей
        String table = """
                    DROP TABLE IF EXISTS users ;
                    """;
        try (PreparedStatement statement = connection.prepareStatement(table)) {
            statement.execute(table);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveUser(String name, String lastName, byte age) {  //сохранить пользователя

        String table = """
                INSERT INTO users (name_id, lastname_id, age_id)
                VALUES (?, ?, ?)
                """;
        try (PreparedStatement statement = connection.prepareStatement(table)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            System.out.printf("User с именем – %s добавлен в базу данных \n", name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void removeUserById(long id) {   //удалить пользователя по идентификатору

        String table = """
                    DELETE FROM users
                    WHERE id = ?;
                    """;
        try (PreparedStatement statement = connection.prepareStatement(table)) {
            statement.setLong(1, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<User> getAllUsers() {      //получить всех пользователей
        List<User> resultList = new ArrayList<>();
        String table = """
                    SELECT * FROM users;
                    """;
        try (Statement statement = connection.createStatement()) {
             ResultSet resultSet = statement.executeQuery(table);

             while (resultSet.next()){
                 User user =new User();
                 user.setId(resultSet.getLong("id"));
                 user.setName(resultSet.getString("name_id"));
                 user.setLastName(resultSet.getString("lastname_id"));
                 user.setAge(resultSet.getByte("age_id"));
                 resultList.add(user);
             }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println( resultList + "\n");

        return resultList;
    }

    public void cleanUsersTable() {  //очистить таблицу пользователей
        String table = """
                    TRUNCATE TABLE users;
                    """;
        try (PreparedStatement statement = connection.prepareStatement(table)) {
            statement.execute(table);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
