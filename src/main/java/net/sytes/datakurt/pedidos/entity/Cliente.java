package net.sytes.datakurt.pedidos.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
public class Cliente {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idCliente;
  
  @Column(nullable = true)
  private String di;
  
  @Column(nullable = true)
  private String nombreCliente;
  
  @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
  private List<Pedido> pedidos;
}
