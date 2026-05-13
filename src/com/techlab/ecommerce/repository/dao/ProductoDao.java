package com.techlab.ecommerce.repository.dao;

import com.techlab.ecommerce.exception.DatabaseConnectionException;
import com.techlab.ecommerce.model.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
//se describe la clase ProductoDao como una capa de acceso a datos (DAO) que se encarga de interactuar con la base de datos para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre los productos. Esta clase utiliza JDBC para ejecutar consultas SQL y manejar los resultados, proporcionando métodos específicos para cada operación relacionada con los productos en la base de datos.DGC 13/05/2026

public class ProductoDao {

    private Connection connection;

    public ProductoDao(Connection connection) {
        this.connection = connection;
    }

    public void add(Producto producto) {
        String sql = "INSERT INTO productos (nombre, precio, stock, categoria_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, producto.getNombre());
            statement.setDouble(2, producto.getPrecio());
            statement.setInt(3, producto.getStock());
            statement.setInt(4, producto.getCategoriaId());
            statement.executeUpdate();
        } catch (SQLException sqle) {

            throw new DatabaseConnectionException("Error al insertar el producto: " + producto.getNombre());
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM productos WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException sqle) {
            throw new DatabaseConnectionException("Error al eliminar el producto: " + id);
        }
    }

    public boolean modify(int id, Producto producto) {
        String sql = "UPDATE productos SET nombre = ?, precio = ?, stock = ?, categoria_id = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, producto.getNombre());
            statement.setDouble(2, producto.getPrecio());
            statement.setInt(3, producto.getStock());
            statement.setInt(4, producto.getCategoriaId());
            statement.setInt(5, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException sqle) {
            throw new DatabaseConnectionException("Error al modificar el producto: " + id);
        }
    }

    public ArrayList<Producto> findAll() {
        String sql = "SELECT * FROM productos ORDER BY nombre";
        ArrayList<Producto> productos = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                productos.add(mapResultSetToProducto(resultSet));
            }
        } catch (SQLException sqle) {
            throw new DatabaseConnectionException("Error al listar los productos" + sqle.getMessage());
        }
        return productos;
    }

    public Producto findByNombre(String nombre) {
        String sql = "SELECT * FROM productos WHERE nombre = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nombre);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToProducto(resultSet);
                }
            }
        } catch (SQLException sqle) {
            throw new DatabaseConnectionException("Error al buscar el producto: " + nombre);
        }
        return null;
    }

    public Producto findById(int id) {
        String sql = "SELECT * FROM productos WHERE id = ?";
        // se considera la refactorizacion para llamar a CallableStatement y usar un procedimiento almacenado en la base de datos, pero se opta por mantenerlo simple con PreparedStatement al momento. Si el proyecto crece y se necesitan consultas más complejas, se podría evaluar esa opción en el futuro.DGC 13/05/2026
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToProducto(resultSet);
                }
            }
        } catch (SQLException sqle) {
            throw new DatabaseConnectionException("Error al buscar el producto con id: " + id);
        }
        return null;
    }

    private Producto mapResultSetToProducto(ResultSet rs) throws SQLException {
        try {
            Producto p = new Producto();
            p.setNombre(rs.getString("nombre"));
            p.setPrecio(rs.getDouble("precio"));
            p.setStock(rs.getInt("stock"));
            p.setCategoriaId(rs.getInt("categoria_id"));
            p.setId(rs.getInt("id"));
            return p;

        } catch (Exception e) {
            throw new DatabaseConnectionException("Error al mapear el resultado a Producto " + e.getMessage());
        }

    }
}
