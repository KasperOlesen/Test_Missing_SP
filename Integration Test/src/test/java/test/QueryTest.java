
package test;

import DBConnections.IDBConnector;
import DBConnections.DBConnectorMock;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import org.junit.After;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;
import persistence.Query;

/**
 *
 * @author kAlex
 */

@RunWith(MockitoJUnitRunner.class)
public class QueryTest {
    IDBConnector con;
    
    @Mock 
    Query mockQuery;
    
    Query query;
    ResultSet resultSetMock = Mockito.mock(ResultSet.class);

    @Before
    public void setUp() throws FileNotFoundException, IOException, SQLException, Exception {
        query = new Query(new DBConnectorMock());
        con = new DBConnectorMock();
        ScriptRunner runner = new ScriptRunner(con.getConnection(), false, false);
        String file = "C:\\Programming\\TEST_missing_SP\\Integration Test\\src\\test\\java\\dbexercise\\dropAll.sql";
        runner.runScript(new BufferedReader(new FileReader(file)));
        String insertFile = "C:\\Programming\\TEST_missing_SP\\Integration Test\\src\\test\\java\\dbexercise\\insertData.sql";
        runner.runScript(new BufferedReader(new FileReader(insertFile)));
    }
  
    @After
    public void afterClass(){
        con.closeConnection();
    }
            
    @Test
    public void testMock() throws SQLException {
        when(mockQuery.selectAllFromDept()).thenReturn(resultSetMock);
        when(resultSetMock.getString(1)).thenReturn("abc");
        String value = mockQuery.selectAllFromDept().getString(1);
        assertThat(value, is("abc"));        
    }
    
    @Test 
    public void testConnection() throws Exception{
        assertThat(con.getConnection(), is(not(CoreMatchers.nullValue())));
    }
    
    @Test
    public void testMockDataFromDb() throws SQLException{
        ResultSet resultSet = query.selectAllFromDept();
        resultSet.next();
        String str = resultSet.getString(2);
        assertThat(str, is("Comp. Sci."));
    }
}
