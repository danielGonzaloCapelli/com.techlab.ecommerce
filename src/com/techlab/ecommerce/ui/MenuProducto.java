package com.techlab.ecommerce.ui;

import com.techlab.ecommerce.model.Producto;
import com.techlab.ecommerce.service.ProductoService;
import com.techlab.ecommerce.utils.Validador;
import java.util.List;
import java.util.Scanner;

/**
 * Maneja la interacción con el usuario a través del menú de consola.
 *
 * Esta clase es responsable de:
 *  - Mostrar el menú al usuario.
 *  - Pedirle los datos necesarios para cada operación.
 *  - Mostrar los resultados o mensajes informativos.
 *
 * No contiene lógica de negocio: para todo lo que tenga que ver
 * con guardar, buscar, actualizar o eliminar productos, delega en
 * el ProductoService. Tampoco controla el flujo del programa: eso
 * lo hace el Main, que decide cuándo llamar a cada método de esta
 * clase y atrapa las excepciones que puedan ocurrir.
 */
public class MenuProducto {

    // Atributos: el Scanner y el Service que esta clase necesita
    // para hacer su trabajo. Se reciben por constructor (no se
    // crean acá adentro) para que quien instancia la clase tenga
    // el control sobre qué Scanner y qué Service usar. Esto se
    // llama "inyección por constructor" y es el mismo patrón que
    // van a ver en Spring Boot.
    private final Scanner sc;
    private final ProductoService service;

    public MenuProducto(Scanner sc, ProductoService service) {
        this.sc = sc;
        this.service = service;
    }

  
    public void mostrarMenu() {
        System.out.println("======= TechLab - Daniel Capelli C26138 Gestión de Productos  =======");
        System.out.println("1) Agregar producto");
        System.out.println("2) Listar productos");
        System.out.println("3) Buscar producto por ID");
        System.out.println("4) Actualizar producto");
        System.out.println("5) Eliminar producto");
        System.out.println("6) Salir");
        System.out.println("=====================================================================");
    }

  

    public void agregarProducto() {
        System.out.println("--- Nuevo producto ---");
        String nombre = Validador.leerTexto(sc, "Nombre: ");
        double precio = Validador.leerDouble(sc, "Precio: ");
        int stock = Validador.leerEntero(sc, "Stock: ");
        int categoriaId = Validador.leerEntero(sc, "ID de categoría: ");

        Producto p = new Producto(nombre, precio, stock, categoriaId);
        Producto guardado = service.guardar(p);

        System.out.println("✔ Producto agregado con id " + guardado.getId());
    }

    public void listarProductos() {
        
        List<Producto> lista = service.listarTodos();

        if (lista.isEmpty()) {
            System.out.println("No hay productos cargados.");
            return;
        }

        System.out.println("--- Catálogo ---");
        for (Producto p : lista) {
            
            System.out.println(p);
        }
    }

    public void buscarProducto() {
        int id = Validador.leerEntero(sc, "Ingrese el id del producto: ");
        
        Producto p = service.obtenerPorId(id);
        System.out.println("Encontrado: " + p);
    }

    public void actualizarProducto() {
        int id = Validador.leerEntero(sc, "Ingrese el id del producto a actualizar: ");

        
        Producto actual = service.obtenerPorId(id);
        System.out.println("Datos actuales: " + actual);

        System.out.println("--- Ingrese los nuevos datos ---");
        String nombre = Validador.leerTexto(sc, "Nombre: ");
        double precio = Validador.leerDouble(sc, "Precio: ");
        int stock = Validador.leerEntero(sc, "Stock: ");
        int categoriaId = Validador.leerEntero(sc, "ID de categoría: ");

        Producto datos = new Producto(nombre, precio, stock, categoriaId);
        Producto actualizado = service.actualizar(id, datos);

        System.out.println("Producto actualizado: " + actualizado);
    }

    public void eliminarProducto() {
        int id = Validador.leerEntero(sc, "Ingrese el id del producto a eliminar: ");
        service.eliminar(id);
        System.out.println("Producto eliminado.");
    }
}