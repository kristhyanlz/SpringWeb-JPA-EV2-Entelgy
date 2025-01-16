package net.sytes.datakurt.pedidos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
  
  //@JsonIgnore
  @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
  //@JsonManagedReference
  @JsonIgnoreProperties("cliente")
  private List<Pedido> pedidos;
  
  public void addPedido(Pedido pedido){
    pedidos.add(pedido);
    pedido.setCliente(this);
  }
  
  public void removePedido(Pedido pedido){
    pedidos.remove(pedido);
    pedido.setCliente(null);
  }
}
