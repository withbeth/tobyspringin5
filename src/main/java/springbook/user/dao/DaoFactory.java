package springbook.user.dao;

public class DaoFactory {

    public UserDao userDao() {
        return new UserDao(mysqlConnectionMaker());
    }

    private ConnectionMaker mysqlConnectionMaker() {
        return new MySqlConnectionMaker();
    }

}
