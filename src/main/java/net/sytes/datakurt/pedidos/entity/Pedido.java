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
  
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)//, mappedBy = "pedidos")
  private List<Producto> productos;
  
  @Column(nullable = false)
  private String direccionEnvio;
  
  @Column(nullable = false)
  private BigDecimal costoTotal;
  
}
