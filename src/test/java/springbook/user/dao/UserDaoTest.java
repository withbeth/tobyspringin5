package springbook.user.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import springbook.user.domain.User;

class UserDaoTest {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        User newUser = new User("1", "withbeth", "qwer");

        UserDao dao = new DaoFactory().userDao();

        dao.add(newUser);

        User createdUser = dao.get(newUser.getId());
        System.out.println("createdUser = " + createdUser);
    }
}