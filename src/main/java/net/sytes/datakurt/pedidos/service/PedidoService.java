package net.sytes.datakurt.pedidos.service;

import lombok.RequiredArgsConstructor;
import net.sytes.datakurt.pedidos.entity.Pedido;
import net.sytes.datakurt.pedidos.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PedidoService {
  
  private final PedidoRepository pedidoRepository;
  
  public List<Pedido> findAll(){
    return pedidoRepository.findAll();
  }
  
  public Optional<Pedido> getPedidoById(Long id){
    return pedidoRepository.findById(id);
  }
  
  public Pedido savePedido(Pedido pedido) {
    return pedidoRepository.save(pedido);
  }
  
  public void deletePedido(Long id) {
    pedidoRepository.deleteById(id);
  }
}
