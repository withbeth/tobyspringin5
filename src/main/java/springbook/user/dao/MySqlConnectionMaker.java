package springbook.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnectionMaker implements ConnectionMaker {

    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        // Class.forName() 메서드 실행만으로 manually loading the class
        Class.forName("com.mysql.jdbc.Driver");

        // The basic service for managing a set of JDBC drivers.
        // - `javax.sql.DataSource` interface provides another way to connect to a data source.
        //  - The use of a DataSource object is the preferred means of connecting to a data source.
        Connection connection = DriverManager.getConnection(
            "jdbc:mysql://127.0.0.1:3306/springbook",
            System.getProperty("mysql.user"),
            System.getProperty("mysql.password"));

        return connection;
    }
}
