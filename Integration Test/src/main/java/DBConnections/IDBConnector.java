
package DBConnections;

import java.sql.Connection;

/**
 *
 * @author kAlex
 */

public interface IDBConnector {
    public IDBConnector  getInstance();
    public Connection getConnection() throws Exception;
    public void closeConnection(); 
}
