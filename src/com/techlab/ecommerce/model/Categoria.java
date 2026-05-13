package com.techlab.ecommerce.model;

public class Categoria {
  private int id;
  private String nombre;
  private String descripcion;
  private String codigo;

  public Categoria(String nombre, String descripcion, String codigo) {
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.codigo = codigo;
  }
  public Categoria() {
  }
  public String getNombre() {
    return nombre;
  }
  public String getDescripcion() {
    return descripcion;
  }
  public String getCodigo() {
    return codigo;
  }
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }
  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }
  public void setId(int id) {
    this.id = id;
  }
  public int getId() {
    return id;
  }
  @Override
  public String toString() {
    return "Categoria: " + nombre + " (" + codigo + ") - " + descripcion + " [ID: " + id + "]";
  }
}
