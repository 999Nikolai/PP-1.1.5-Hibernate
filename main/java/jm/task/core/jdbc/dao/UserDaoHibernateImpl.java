package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;



public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }
    Session session = Util.openHib();

    @Override
    public void createUsersTable() {
        SQLQuery queryUpdate = session.createSQLQuery("""
                CREATE TABLE IF NOT EXISTS users (
                id BIGINT PRIMARY KEY AUTO_INCREMENT,
                name_id VARCHAR(40),
                lastname_id VARCHAR(40),
                age_id TINYINT
                )
                """);
        try {
            session.beginTransaction();

            queryUpdate.executeUpdate();
            session.getTransaction()
                    .commit();
        } catch (Exception e) {
            session.getTransaction()
                    .rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            session.beginTransaction();
            SQLQuery queryUpdate = session.createSQLQuery("""
                    DROP TABLE IF EXISTS users ;
                    """);
            queryUpdate.executeUpdate();
            session.getTransaction()
                    .commit();
        } catch (Exception e) {
            session.getTransaction()
                    .rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction()
                    .commit();
        } catch (Exception e) {
            session.getTransaction()
                    .rollback();
            e.printStackTrace();
        }
        System.out.printf("User с именем – %s добавлен в базу данных \n", name);

    }

    @Override
    public void removeUserById(long id) {
        try {
            session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            session.getTransaction()
                    .commit();
        } catch (Exception e) {
            session.getTransaction()
                    .rollback();
            e.printStackTrace();
        }

    }

    @Override

    public List<User> getAllUsers() {
        List resultList = new ArrayList<>();
        try {
            session.beginTransaction();
            resultList = session.createQuery("from User ")
                    .getResultList();
            session.getTransaction()
                    .commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        System.out.println(resultList);

        return resultList;
    }

    @Override
    public void cleanUsersTable() {
        try {
            session.beginTransaction();
            session.createQuery("delete User ").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
