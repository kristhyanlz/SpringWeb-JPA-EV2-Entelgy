package net.sytes.datakurt.pedidos.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idPedido;
  
  @Column(nullable = false)
  private LocalDate fechaCompra;
  
  @ManyToOne
  @JoinColumn(name = "cliente_id", referencedColumnName = "idCliente")//Descomentando el ref
  //@JsonBackReference
  @JsonIgnoreProperties("pedidos")
  private Cliente cliente;
  
  @ManyToMany
  @JsonIgnoreProperties("pedidos")
  @JoinTable(
      name = "pedidos_productos", // Nombre de la tabla intermedia
      joinColumns = @JoinColumn(name = "id_pedido"), // FK Pedido
      inverseJoinColumns = @JoinColumn(name = "id_producto") // FK Producto
  )
  private List<Producto> productos;
  
  public boolean addProducto(Producto producto){
    return productos.add(producto);
  }
  
  public boolean removeProducto(Producto producto){
    return productos.remove(producto);
  }
  
  @Column(nullable = false)
  private String direccionEnvio;
  
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private EstadoPedido estadoPedido;
  
  @Column(nullable = false)
  private BigDecimal costoTotal;
}
