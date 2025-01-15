package net.sytes.datakurt.pedidos.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Data
@NoArgsConstructor
public class Pedido {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idPedido;
  
  @Column(nullable = false)
  private LocalDate fechaCompra;
  
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "cliente_id", referencedColumnName = "idCliente")
  private Cliente cliente;
  
  @ManyToMany(cascade = CascadeType.ALL)//, mappedBy = "pedidos")
  @JoinTable(
      name = "pedidos_productos", // Nombre de la tabla intermedia
      joinColumns = @JoinColumn(name = "id_pedido"), // FK hacia Estudiante
      inverseJoinColumns = @JoinColumn(name = "id_producto") // FK hacia Curso
  )
  private List<Producto> productos;
  
  public void addProducto(Producto producto){
    productos.add(producto);
    //producto.set
  }
  
  @Column(nullable = false)
  private String direccionEnvio;
  
  @Column(nullable = false)
  private BigDecimal costoTotal;
  
}
