package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import springbook.user.domain.User;

public class UserDao {
    private final DataSource dataSource;

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void add(User user) throws SQLException {
        Connection connection = dataSource.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
            "insert into users(id, name, password) values(?, ?, ?)");
        preparedStatement.setString(1, user.getId());
        preparedStatement.setString(2, user.getName());
        preparedStatement.setString(3, user.getPassword());

        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();
    }

    public User get(String userId) throws SQLException {
        Connection connection = dataSource.getConnection();

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
}
