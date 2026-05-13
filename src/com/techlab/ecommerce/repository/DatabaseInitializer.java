package com.techlab.ecommerce.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
// Clase para inicializar la base de datos y crear las tablas necesarias ehn caso de que no existan. Esto es útil para facilitar la configuración inicial del proyecto y evitar errores por falta de tablas o base de datos. DGC 13/05/2026
public class DatabaseInitializer {

  private static final String URL_SERVER = "jdbc:mysql://localhost:3306/";
  private static final String DB_NAME = "ecommerce";
  private static final String USER = "root";
  private static final String PASSWORD = ""; // Esta en blanco por inicializacion local y para prueba del proyecto DGC
                                             // 13/05/2026

  public static void initialize() {

    try (Connection conn = DriverManager.getConnection(URL_SERVER, USER, PASSWORD);
        Statement stmt = conn.createStatement()) {

      // 2. Crear la base de datos si no existe
      stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
      System.out.println("Base de datos verificada/creada.");

      // 3. Conectarse a la base de datos recién creada/verificada
      stmt.executeUpdate("USE " + DB_NAME);

      // 4. Crear tabla Categorías (necesaria para la FK de Productos)
      String sqlCategorias = "CREATE TABLE IF NOT EXISTS categorias ("
          + "id INT AUTO_INCREMENT PRIMARY KEY, "
          + "nombre VARCHAR(100) NOT NULL UNIQUE"
          + ")";
      stmt.executeUpdate(sqlCategorias);

      // 5. Crear tabla Productos con la columna de versión para concurrencia
      String sqlProductos = "CREATE TABLE IF NOT EXISTS productos ("
          + "id INT AUTO_INCREMENT PRIMARY KEY, "
          + "nombre VARCHAR(100) NOT NULL, "
          + "precio DOUBLE NOT NULL, "
          + "stock INT NOT NULL, "
          + "categoria_id INT)";
          /*+ "version INT DEFAULT 0, "
          + "FOREIGN KEY (categoria_id) REFERENCES categorias(id)"
          + ")";
          Se comenta esta parte paa no tener que crear en este momento la FK y el campo versón el cual esta pensado para el carro de compras (detalle_compra_tmp valide si el stock es suficiente)*/
      stmt.executeUpdate(sqlProductos);

      System.out.println("Tablas de base de datos listas para usar.");

    } catch (SQLException e) {
      System.err.println("Error crítico al inicializar la base de datos: " + e.getMessage());
    }
  }
}