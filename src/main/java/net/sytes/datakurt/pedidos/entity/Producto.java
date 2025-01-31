package net.sytes.datakurt.pedidos.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
public class Producto {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idProducto;
  
  @Column(nullable = false)
  private String marcaProducto;
  
  @Column(nullable = false)
  private String nombreProducto;
  
  @Column
  private String descripcionProducto;
  
  @Column(nullable = false)
  private BigDecimal precioProducto;
  
  @ManyToMany(mappedBy = "productos")
  @JsonIgnoreProperties("productos")
  private List<Pedido> pedidos;
  
  public void addPedido(Pedido pedido){
    pedidos.add(pedido);
    
    List<Producto> listaProductos =  pedido.getProductos();
    listaProductos.add(this);
    pedido.setProductos(listaProductos);
  }
  
  public void removePedido(Pedido pedido){
    pedidos.remove(pedido);
    
    List<Producto> listaProductos =  pedido.getProductos();
    listaProductos.remove(this);
    pedido.setProductos(listaProductos);
  }
}
