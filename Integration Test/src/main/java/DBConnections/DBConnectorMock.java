package DBConnections;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author kAlex
 */

public class DBConnectorMock implements IDBConnector {
    private Connection con;
    private DBConnector instance;
    private String url;
    private String username;
    private String password;
    private String driver;

    public DBConnectorMock() {
        Properties props = new Properties();
        try {
            InputStream stream = DBConnector.class.getResourceAsStream("db.properties");
            props.load(stream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.driver = props.getProperty("ORACLE_DRIVER_CLASS");
        this.url = props.getProperty("ORACLE_URL");
        this.username = props.getProperty("ORACLE_USERNAME");
        this.password = props.getProperty("ORACLE_PASSWORD");
    }

    public Connection getConnection() throws Exception {
        Class.forName(this.driver);
        return DriverManager.getConnection(this.url, this.username, this.password);
    }

    public DBConnector getInstance() {
        if (instance == null) {
            instance = new DBConnector();
        }
        return instance;
    }

    @Override
    public void closeConnection() {
        try {
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
