package com.techlab.ecommerce;

import com.techlab.ecommerce.exception.ProductoNoEncontradoException;
import com.techlab.ecommerce.exception.StockInsuficienteException;
import com.techlab.ecommerce.repository.DatabaseInitializer;
import com.techlab.ecommerce.service.ProductoService;
import com.techlab.ecommerce.ui.MenuProducto;
import com.techlab.ecommerce.utils.Validador;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // verifico que exista la base de datos y las tablas necesarias antes de
        // arrancar el programa DGC 13/05/2026
        DatabaseInitializer.initialize();
        ProductoService service = new ProductoService();
        Scanner sc = new Scanner(System.in);
        MenuProducto menu = new MenuProducto(sc, service);

        /*
         * se anula la carga de datos de prueba para evitar conflictos con la base de
         * datos real, pero se deja el método comentado para facilitar futuras pruebas o
         * demostraciones del proyecto DGC 13/05/2026 (Se modificó el método para que no
         * se creen productos con ID fijo y se deje que la base de datos asigne el ID
         * automáticamente, lo cual es más realista y evita problemas de duplicados en
         * futuras ejecuciones del programa)
         * //cargarDatosDePrueba(service);
         */

        int opcion;

        do {
            menu.mostrarMenu();
            opcion = Validador.leerEntero(sc, "Elija una opción: ");

            try {
                switch (opcion) {
                    case 1 -> menu.agregarProducto();
                    case 2 -> menu.listarProductos();
                    case 3 -> menu.buscarProducto();
                    case 4 -> menu.actualizarProducto();
                    case 5 -> menu.eliminarProducto();
                    case 6 -> System.out.println("¡Hasta luego!");
                    default -> System.out.println("Opción inválida. Elija un número del 1 al 6.");
                }
            } catch (ProductoNoEncontradoException | StockInsuficienteException e) {

                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {

                System.out.println("Dato inválido: " + e.getMessage());
            }

            System.out.println();

        } while (opcion != 6);

        sc.close();
    }

    /*
     * se comenta el método de carga de datos de prueba para evitar conflictos con
     * la base de datos real, pero se deja el código para facilitar futuras pruebas
     * o demostraciones del proyecto DGC 13/05/2026
     * private static void cargarDatosDePrueba(ProductoService service) {
     * service.guardar(new Producto("Café molido 500g", 4500, 30, "Bebidas"));
     * service.guardar(new Producto("Yerba mate 1kg", 3200, 50, "Bebidas"));
     * service.guardar(new Producto("Galletitas dulces", 1850, 100, "Almacén"));
     * service.guardar(new Producto("Aceite de oliva 500ml", 6700, 20, "Almacén"));
     * service.guardar(new Producto("Chocolate amargo 70%", 2900, 15, "Golosinas"));
     * System.out.println("✔ Se cargaron 5 productos de prueba.\n");
     * }
     */
}