package springbook.user.dao;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@Configuration
public class DaoFactory {

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/springbook");
        dataSource.setUsername(System.getProperty("mysql.user"));
        dataSource.setPassword(System.getProperty("mysql.password"));
        return dataSource;
    }

    @Bean
    public UserDao userDao() {
        return new UserDao(dataSource());
    }

    @Bean
    public ConnectionMaker mysqlConnectionMaker() {
        return new MySqlConnectionMaker();
    }

}
