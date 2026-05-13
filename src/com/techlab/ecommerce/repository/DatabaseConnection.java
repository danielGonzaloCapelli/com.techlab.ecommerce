package com.techlab.ecommerce.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.techlab.ecommerce.exception.DatabaseConnectionException;



public class DatabaseConnection {
   private Connection connection;
  private static final String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";
  private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/ecommerce";
  private static final String USERNAME = "root";
  private static final String PASSWORD = "";
  public Connection getConnection()  {
    try {
      Class.forName(MYSQL_DRIVER);
      connection = DriverManager.getConnection(MYSQL_URL, USERNAME, PASSWORD);
      System.out.println("Conectado!");
    } catch (ClassNotFoundException cnfe) {
      throw new  DatabaseConnectionException("No se ha encontrado el driver de MySQL. Asegúrate de tener el conector JDBC en el classpath.");
    
    } catch (SQLException sqle) {
      throw new DatabaseConnectionException("No se ha podido conectar con el servidor de base de datos. Comprueba que los datos son correctos y que el servidor se ha iniciado " );
    }
    return connection;
  }
 
  public void close() {
    try {
      if (connection != null && !connection.isClosed()) {
        connection.close();
      }
    } catch (SQLException sqle) {
      throw new DatabaseConnectionException("No se ha podido cerrar la conexión con la base de datos");
    }
  }
}
