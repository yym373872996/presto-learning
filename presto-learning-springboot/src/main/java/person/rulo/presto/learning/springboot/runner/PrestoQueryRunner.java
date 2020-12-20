package person.rulo.presto.learning.springboot.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Component;
import scala.Tuple3;

import javax.sql.RowSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author rulo
 * @Date 2020/12/20 18:09
 */
@Component
public class PrestoQueryRunner implements ApplicationRunner {

    private final Logger logger = LoggerFactory.getLogger(PrestoQueryRunner.class);

    @Autowired
    @Qualifier("prestoTemplate")
    private JdbcTemplate prestoTemplate;

    @Override
    public void run(ApplicationArguments args) {
        String sql = "select * from hive.test.user_device_gender a left join mysql.test.device_purchase b on a.device_id = b.device_id";
        SqlRowSet rowSet = prestoTemplate.queryForRowSet(sql);
        SqlRowSetMetaData rowSetMetaData = rowSet.getMetaData();
        while (rowSet.next()) {
            Map map = new HashMap<String, Tuple3>();
            for (int i = 1; i <= rowSetMetaData.getColumnCount(); i++) {
                Tuple3<String, String, String> tuple3 = new Tuple3<>(rowSetMetaData.getColumnTypeName(i), rowSetMetaData.getColumnClassName(i), rowSet.getString(i));
                map.put(rowSetMetaData.getColumnName(i), tuple3);
            }
            logger.info("Presto query result: {}", map.toString());
        }
    }
}
