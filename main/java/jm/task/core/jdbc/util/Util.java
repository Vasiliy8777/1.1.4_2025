package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;

import org.hibernate.boot.registry.StandardServiceRegistry;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;



public class Util {
    private static final String dbName = "newbd1_23";
    private static final String dbUser = "root";
    private static final String dbPass = "rootuser";
    private static final String dbUrl = "jdbc:mysql://localhost:3306/" + dbName;
    //private static final SessionFactory sessionFactory = buildSessionFactory();

    // реализуйте настройку соеденения с БД

    public static Connection getConnect() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUser, dbPass);
    }

    public static SessionFactory getSessionFactory() {
        try {
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .applySetting("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                    .applySetting("hibernate.connection.url", dbUrl)
                    .applySetting("hibernate.connection.username", "root")
                    .applySetting("hibernate.connection.password", "rootuser")
                    .applySetting("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                    .applySetting("hibernate.show_sql", "true").build();
            Metadata metadata = new MetadataSources(registry)
                    .addAnnotatedClass(jm.task.core.jdbc.model.User.class)
                    .buildMetadata();
            return metadata.buildSessionFactory();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка создания SessionFactory", e);
        }
    }
}
