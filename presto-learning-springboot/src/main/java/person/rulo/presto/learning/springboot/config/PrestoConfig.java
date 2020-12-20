package person.rulo.presto.learning.springboot.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @Author rulo
 * @Date 2020/12/20 18:02
 */
@Configuration
public class PrestoConfig {

    @Bean(name = "prestoDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.presto")
    public DataSource prestoDataSource() {
        return new DruidDataSource();
    }

    @Bean(name = "prestoTemplate")
    public JdbcTemplate prestoJdbcTemplate(@Qualifier("prestoDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
