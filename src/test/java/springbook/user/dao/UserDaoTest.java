package springbook.user.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springbook.user.domain.User;

class UserDaoTest {

    public static void main(String[] args) throws SQLException {

        ApplicationContext ac = new AnnotationConfigApplicationContext(DaoFactory.class);

        UserDao dao = ac.getBean(UserDao.class);

        User newUser = new User("2", "angela", "password");

        dao.add(newUser);

        User createdUser = dao.get(newUser.getId());
        System.out.println("createdUser = " + createdUser);
    }
}