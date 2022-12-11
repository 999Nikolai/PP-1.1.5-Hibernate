package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserService service = new UserServiceImpl();
        service.createUsersTable();
        // System.out.println("Таблица создана");
        service.saveUser("Ivan", "Ivanov", (byte) 30);

        service.removeUserById(2);
        //System.out.println("Удалил пользователя №2");

        service.getAllUsers();
        //System.out.println("Получил всех пользователей");

        service.cleanUsersTable();
        //System.out.println("Таблица очищена");
        service.dropUsersTable();
        //System.out.println("Таблица удалена");
        Util.connectClose();
        Util.sessionClose();


        // реализуйте алгоритм здесь

    }
}
