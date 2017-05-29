package persistence;

import DBConnections.IDBConnector;
import java.sql.*;

/**
 *
 * @author kAlex
 */

public class Query {
    private IDBConnector dbConnection;
    private Connection con;

    public Query(IDBConnector dbConnection) throws Exception {
        this.dbConnection = dbConnection;
        this.con = dbConnection.getConnection();
    }

    public ResultSet selectAllFromDept() throws SQLException {
        String query = "SELECT * FROM DEPARTMENT";
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = con.prepareStatement(query);
            rs = statement.executeQuery();
        } catch (Exception ex) {
            System.err.println(ex);
        }
        return rs;
    }
}
