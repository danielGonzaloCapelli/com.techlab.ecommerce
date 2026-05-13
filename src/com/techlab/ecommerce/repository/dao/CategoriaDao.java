package com.techlab.ecommerce.repository.dao;

import com.techlab.ecommerce.exception.DatabaseConnectionException;
import com.techlab.ecommerce.model.Categoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList; 

public class CategoriaDao {
  private Connection connection;
  public CategoriaDao(Connection connection) {
    this.connection = connection;
  }
  public boolean agregarCategoria(Categoria categoria) {
    String sql = "INSERT INTO categorias (nombre, descripcion, codigo) VALUES (?, ?, ?)";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setString(1, categoria.getNombre());
      statement.setString(2, categoria.getDescripcion());
      statement.setString(3, categoria.getCodigo());
      return statement.executeUpdate() == 1;
    } catch (SQLException sqle) {
      throw new DatabaseConnectionException("Error al agregar la categoría: " + categoria.getNombre());
    }
  }
  public boolean eliminarCategoria(int id) {
    String sql = "DELETE FROM categorias WHERE id = ?";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, id);
      return statement.executeUpdate() == 1;
    } catch (SQLException sqle) {
      throw new DatabaseConnectionException("Error al eliminar la categoría: " + id);
    }
  }
  public boolean modificarCategoria(int id, Categoria categoria) {
    String sql = "UPDATE categorias SET nombre = ?, descripcion = ?, codigo = ? WHERE id = ?";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setString(1, categoria.getNombre());
      statement.setString(2, categoria.getDescripcion());
      statement.setString(3, categoria.getCodigo());
      statement.setInt(4, id);
      return statement.executeUpdate() == 1;
    } catch (SQLException sqle) {
      throw new DatabaseConnectionException("Error al modificar la categoría: " + id);
    }
  }
  public ArrayList<Categoria> listarCategorias() {
    String sql = "SELECT id, nombre, descripcion FROM categorias ORDER BY nombre";
    ArrayList<Categoria> categorias = new ArrayList<>();
    try (PreparedStatement statement = connection.prepareStatement(sql);
         ResultSet resultSet = statement.executeQuery()) {
      while (resultSet.next()) {
        Categoria categoria = new Categoria();
        categoria.setId(resultSet.getInt("id"));
        categoria.setNombre(resultSet.getString("nombre"));
        categoria.setDescripcion(resultSet.getString("descripcion"));
        categoria.setCodigo(resultSet.getString("codigo"));
        categorias.add(categoria);
      }
    } catch (SQLException sqle) {
      throw new DatabaseConnectionException("Error al listar las categorías");
    }
    return categorias;
  }
}