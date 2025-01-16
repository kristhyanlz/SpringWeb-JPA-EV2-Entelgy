package net.sytes.datakurt.pedidos.entity;

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
  
  @ManyToMany(mappedBy = "productos") // Relación inversa
  private List<Pedido> pedidos;
}
