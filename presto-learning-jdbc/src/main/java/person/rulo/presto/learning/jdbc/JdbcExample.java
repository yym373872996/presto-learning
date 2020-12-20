package person.rulo.presto.learning.jdbc;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author rulo
 * @Date 2020/12/20 17:14
 */
public class JdbcExample {
    public static void main(String[] args) throws Exception {
        Class.forName("com.facebook.presto.jdbc.PrestoDriver");
        // 此处的 username 是连 presto 的，如果没有配置，可以传任意值，但不能为空
        Connection connection = DriverManager.getConnection("jdbc:presto://localhost:8080","root",null);
        Statement stmt = connection.createStatement();
        String sql = "select * from hive.test.user_device_gender a left join mysql.test.device_purchase b on a.device_id = b.device_id";
        ResultSet rs = stmt.executeQuery(sql);
        ResultSetMetaData resultSetMetaData = rs.getMetaData();
        while (rs.next()) {
            Map map = new HashMap<String, String>();
            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                map.put(resultSetMetaData.getColumnName(i), rs.getString(i));
            }
            System.out.println(map);
        }
        rs.close();
        connection.close();
    }
}
