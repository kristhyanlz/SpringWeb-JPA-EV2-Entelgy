package net.sytes.datakurt.pedidos.service;

import lombok.RequiredArgsConstructor;
import net.sytes.datakurt.pedidos.entity.Pedido;
import net.sytes.datakurt.pedidos.repository.PedidoRepository;
import net.sytes.datakurt.pedidos.service.exceptions.PedidoInvalidoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
  
  @Transactional
  public Pedido savePedido(Pedido pedido) {
    //Verificamos la cantidad minima de productos
    int cantidadProductos = pedido.getProductos().size();
    if (cantidadProductos < 3){
      throw new PedidoInvalidoException();
    }
    
    BigDecimal costoEnvio = BigDecimal.ZERO;//Estamos seguros que al menos hay 3 productos
    BigDecimal costoProductos = pedido.getProductos().stream()
        .reduce(
            BigDecimal.ZERO,
            (acum, actual) -> acum.add(actual.getPrecioProducto()),
            BigDecimal::add
        );
    
    if(cantidadProductos <= 10){
      costoEnvio = BigDecimal.valueOf(15);
    }
    costoProductos = costoProductos.add(costoEnvio);
    
    pedido.setCostoTotal(costoProductos);
    return pedidoRepository.save(pedido);
  }
  
  public void deletePedido(Long id) {
    pedidoRepository.deleteById(id);
  }
}