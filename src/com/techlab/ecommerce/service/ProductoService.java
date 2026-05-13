package com.techlab.ecommerce.service;

import com.techlab.ecommerce.exception.ProductoNoEncontradoException;
import com.techlab.ecommerce.model.Producto;
import com.techlab.ecommerce.repository.*;
import com.techlab.ecommerce.repository.dao.ProductoDao;
import com.techlab.ecommerce.utils.Validador;
import java.util.ArrayList;
import java.util.List;
//clase encargada de manejar la lógica de negocio relacionada con los productos, actuando como intermediaria entre la capa de presentación (UI) y la capa de acceso a datos (DAO). Esta clase se encarga de validar los datos de entrada, gestionar las operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre los productos y manejar las excepciones relacionadas con la lógica de negocio. Al centralizar esta lógica en el servicio, se promueve una arquitectura más limpia y mantenible, permitiendo que la UI se enfoque únicamente en la interacción con el usuario y el DAO en la gestión de la persistencia de datos.DGC 13/05/2026
public class ProductoService {

    public ProductoService() {
    }

    private List<Producto> productos = new ArrayList<>();

    private static int contadorId = 1;

    public Producto guardar(Producto p) {
        Validador.validarNombre(p.getNombre());
        Validador.validarPrecio(p.getPrecio());
        Validador.validarStock(p.getStock());
        Validador.validarCategoria(p.getCategoriaId());
        p.setId(contadorId);
        contadorId++;

        // productos.add(p);
        ProductoDao productoDao = new ProductoDao(new DatabaseConnection().getConnection());
        productoDao.add(p);

        return p;
    }

    public ArrayList<Producto> listarTodos() {
        try {
            System.out.println("Obteniendo lista de productos desde la base de datos...");
            ProductoDao productoDao = new ProductoDao(new DatabaseConnection().getConnection());
            return new ArrayList<>(productoDao.findAll());

        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la lista de productos: " + e.getMessage());
        }

    }

    public Producto obtenerPorId(int id) {
        ProductoDao productoDao = new ProductoDao(new DatabaseConnection().getConnection());
        System.out.println("Buscando producto con id " + id);
        return productoDao.findById(id);

    }

    public Producto actualizar(int id, Producto datos) {
        try {
            Producto p = obtenerPorId(id);
            if (p == null) {
                throw new ProductoNoEncontradoException("No se encontró un producto con id " + id);
            }
            Validador.validarNombre(datos.getNombre());
            Validador.validarPrecio(datos.getPrecio());
            Validador.validarStock(datos.getStock());
            Validador.validarCategoria(datos.getCategoriaId());
            p.setNombre(datos.getNombre());
            p.setPrecio(datos.getPrecio());
            p.setStock(datos.getStock());
            p.setCategoriaId(datos.getCategoriaId());
            // se actualiza en la base de datos
            ProductoDao productoDao = new ProductoDao(new DatabaseConnection().getConnection());
            productoDao.modify(id, datos);
            return p;
        } catch (ProductoNoEncontradoException e) {
            throw new ProductoNoEncontradoException("No se encontró un producto con id " + id);

        } catch (Exception e) {
            throw new IllegalArgumentException("Error al actualizar el producto: " + e.getMessage());
        }

    }

    public void eliminar(int id) {
        try {
            Producto p = obtenerPorId(id);
            ProductoDao productoDao = new ProductoDao(new DatabaseConnection().getConnection());
            productoDao.delete(id);
            // productos.remove(p);
        } catch (ProductoNoEncontradoException e) {
            throw new ProductoNoEncontradoException("No se encontró un producto con id " + id);
        }
    }
}
