package Bookstore;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;

public class SqlConnectionPoolFactory{
    private static final String INSTANCE_CONNECTION_NAME = "fine-slice-435302-a8:us-central1:bookstore";

    private static final String DB_USER = "main";
    private static final String DB_PASS = "KB&DnG,PSEM.s\"T;";
    private static final String DB_NAME = "bookstore";

    public static DataSource createConnectionPool() {


        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(String.format("jdbc:mysql:///%s", DB_NAME));
        config.setUsername(DB_USER);

        config.addDataSourceProperty("socketFactory", "com.google.cloud.sql.mysql.SocketFactory");
        config.addDataSourceProperty("cloudSqlInstance", INSTANCE_CONNECTION_NAME);

        return new HikariDataSource(config);
    }
}
