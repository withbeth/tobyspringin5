package springbook.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import springbook.user.domain.User;

/**
 * User 정보 CRUD 담당하는 super simple dao.
 * Q. JDBC API이용한 DAO에 어떤점이 문제점일까?
 * - Expected: 실제 달라지는 로직 외의 커넥션생성 ~ 자원 닫고 결과 반환의 로직 중복
 */
public class UserDao {

    public void add(User user) throws ClassNotFoundException, SQLException {
        // Class.forName() 메서드 실행만으로 manually loading the class
        Class.forName("com.mysql.jdbc.Driver");

        // The basic service for managing a set of JDBC drivers.
        // - `javax.sql.DataSource` interface provides another way to connect to a data source.
        //  - The use of a DataSource object is the preferred means of connecting to a data source.
        Connection connection = DriverManager.getConnection(
            "jdbc:mysql://127.0.0.1:3306/springbook",
            System.getProperty("mysql.user"),
            System.getProperty("mysql.password"));

        PreparedStatement preparedStatement = connection.prepareStatement(
            "insert into users(id, name, password) values(?, ?, ?)");
        preparedStatement.setString(1, user.getId());
        preparedStatement.setString(2, user.getName());
        preparedStatement.setString(3, user.getPassword());

        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();
    }

    public User get(String userId) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
            "jdbc:mysql://127.0.0.1:3306/springbook", "root", "#Nightowl1!");

        PreparedStatement preparedStatement = connection.prepareStatement(
            "select * from users where id = ?");
        preparedStatement.setString(1, userId);

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        User user = new User(
            resultSet.getString("id"),
            resultSet.getString("name"),
            resultSet.getString("password")
        );

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return user;
    }

    // TODO : Test Class로 이동
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        User newUser = new User("1", "withbeth", "qwer");

        UserDao dao = new UserDao();

        dao.add(newUser);

        User createdUser = dao.get(newUser.getId());
        System.out.println("createdUser = " + createdUser);
    }

}
