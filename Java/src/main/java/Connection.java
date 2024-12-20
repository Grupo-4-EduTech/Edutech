import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class Connection {
    private final DataSource dataSource;

    public Connection(){
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl("jdbc:mysql://container-mysql:3306/EduTech");
        basicDataSource.setUsername("root");
        basicDataSource.setPassword("urubu100");

        this.dataSource = basicDataSource;
    }

    public JdbcTemplate getConnection(){
        return new JdbcTemplate(dataSource);
    }
}
