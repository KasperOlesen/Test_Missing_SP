<!DOCTYPE html>
<html>
  <head>
  </head>
  <body>

<h2>INTEGRATION TESTING</h2>
<h3> - Database mocking</h3><br>
<br>
 
<p>Da jeg skal mocke en database, har jeg har valgt at lave klasserne DBConnector og DBConnectorMock. Begge connector til min database ved hjælp af en .properties fil, som de indlæser i constructoren.</p>
<p>Begge klasser implementerer interfacet IDBConnector:</p>

 
public interface IDBConnector { <br>
    public IDBConnector  getInstance(); <br>
    public Connection getConnection() throws Exception; <br>
    public void closeConnection();  <br>
} <br>
 <br>
<br>
<b>Test:</b> <br> 
<p>Da jeg gerne vil kontrollere databasens indhold under tests, har jeg valgt at inkludere to filer; én til at bygge strukturen og én til at indsætte data. Begge filer bliver brugt i setUp() metoden.</p>
<p>Jeg har lavet tre simple test metoder, som tester forbindelsen til databasen, mocking og data fra en mock database.</p>
 
</p>Forbindelsen til databasen bliver lukket i afterClass() metoden.</p>
  </body>