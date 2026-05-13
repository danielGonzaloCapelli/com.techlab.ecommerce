package com.techlab.ecommerce.service;
import com.techlab.ecommerce.exception.CategoriaNoEncontradaException;
import com.techlab.ecommerce.model.Categoria;
import java.util.ArrayList;
import java.util.List;
public class CategoriaService {
  private List<Categoria> categorias;

  public CategoriaService() {
    this.categorias = new ArrayList<>();
  }

  public void agregarCategoria(Categoria categoria) {
    categorias.add(categoria);
  }

  public List<Categoria> obtenerCategorias() {
    return categorias;
  }

  public Categoria obtenerCategoriaPorCodigo(String codigo) {
    for (Categoria categoria : categorias) {
      if (categoria.getCodigo().equals(codigo)) {
        return categoria;
      }
    }
    throw new CategoriaNoEncontradaException("Categoría con código " + codigo + " no encontrada.");
  }
}