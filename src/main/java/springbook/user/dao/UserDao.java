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
 * - Answer :
 *  - 기본적으로, 관심사를 분리하여 유연한 변경을 가능케 하자.
 *  - UserDao는 add()에서만 적어도 3가지 관심사항 발견.
 *      - 1) DB와 커넥션을 어떻게 가져 올 것인가.
 *      - 2) SQL을 담을 Statement(PreparedStatement) 작성 과 실행
 *      - 3) 작업이 끝난후 리소스 해제
 *      - 4) 예외상황 처리 (아직 미구현되있지만)
 */
public class UserDao {

    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection connection = getConnection();

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
        Connection connection = getConnection();

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

    private static Connection getConnection() throws ClassNotFoundException, SQLException {
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

    // TODO : Test Class로 이동
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        User newUser = new User("1", "withbeth", "qwer");

        UserDao dao = new UserDao();

        dao.add(newUser);

        User createdUser = dao.get(newUser.getId());
        System.out.println("createdUser = " + createdUser);
    }

}
