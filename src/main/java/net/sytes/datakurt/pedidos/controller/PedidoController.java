package net.sytes.datakurt.pedidos.controller;

import lombok.RequiredArgsConstructor;
import net.sytes.datakurt.pedidos.entity.Cliente;
import net.sytes.datakurt.pedidos.entity.EstadoPedido;
import net.sytes.datakurt.pedidos.entity.Pedido;
import net.sytes.datakurt.pedidos.entity.Producto;
import net.sytes.datakurt.pedidos.service.ClienteService;
import net.sytes.datakurt.pedidos.service.PedidoService;
import net.sytes.datakurt.pedidos.service.ProductoService;
import net.sytes.datakurt.pedidos.service.exceptions.PedidoInvalidoException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {
  private final PedidoService pedidoService;
  private final ProductoService productoService;
  private final ClienteService clienteService;
  
  @GetMapping
  public List<Pedido> getPedidos(){
    return pedidoService.findAll();
  }
  
  @GetMapping("/{id}")
  public Optional<Pedido> getPedido(@PathVariable("id") Long id){
    return pedidoService.getPedidoById(id);
  }
  
  @PostMapping
  public ResponseEntity<?> createPedido(@RequestBody Pedido pedido) {
    try{
      Pedido savedPedido = pedidoService.savePedido(pedido);
      return ResponseEntity.ok(savedPedido);
    } catch (PedidoInvalidoException e){
      return ResponseEntity.badRequest().body(e.getMessage());
    }
    
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updatePedido(@PathVariable Long id, @RequestBody Pedido pedidoDetails){
    Optional<Pedido> pedidoOptional = pedidoService.getPedidoById(id);
    if (pedidoOptional.isPresent()){
      Pedido updatedPedido = pedidoOptional.get();
      updatedPedido.setFechaCompra(pedidoDetails.getFechaCompra());
      updatedPedido.setEstadoPedido(pedidoDetails.getEstadoPedido());
      //Costo total se calcula en el service
      updatedPedido.setDireccionEnvio(pedidoDetails.getDireccionEnvio());
      
      //La FK se busca por ID para evitar modificarlo
      Optional<Cliente> cliente = clienteService.getClienteById(
          pedidoDetails.getCliente().getIdCliente()
      );
      cliente.ifPresent(updatedPedido::setCliente);
      
      //Limpiamos los productos para asegurar la asociación
      updatedPedido.getProductos().clear();
      //Volvemos a agregar a todos los productos
      for(Producto _producto : pedidoDetails.getProductos()){
        Optional<Producto> producto = productoService.getProductoById(_producto.getIdProducto());
        producto.ifPresent(updatedPedido::addProducto);
      }
      
      try{
        Pedido savedPedido = pedidoService.savePedido(updatedPedido);
        return ResponseEntity.ok(savedPedido);
      } catch (PedidoInvalidoException e){
        return ResponseEntity.badRequest().body(e.getMessage());
      }
    }
    return ResponseEntity.notFound().build();
  }
  
  @PutMapping("/{id}/estadoPedido")
  public ResponseEntity<?> updateEstadoPedido(@PathVariable Long id, @RequestBody Pedido pedidoDetails){
    Optional<Pedido> pedidoOptional = pedidoService.getPedidoById(id);
    EstadoPedido estadoPedido = pedidoDetails.getEstadoPedido();
    if(pedidoOptional.isPresent()){
      Pedido pedido = pedidoOptional.get();
      pedido.setEstadoPedido(estadoPedido);
      return ResponseEntity.ok(pedidoService.savePedido(pedido));
    }
    return ResponseEntity.notFound().build();
  }
  
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePedido(@PathVariable Long id){
    pedidoService.deletePedido(id);
    return ResponseEntity.noContent().build();
  }
  
  @PostMapping("/{id}/producto")
  public ResponseEntity<?> addProducto(@PathVariable Long id, @RequestBody Producto reqProducto){
    Optional<Producto> productoOptional = productoService.getProductoById(reqProducto.getIdProducto());
    Optional<Pedido> pedidoOptional = pedidoService.getPedidoById(id);
    if (productoOptional.isPresent() && pedidoOptional.isPresent()){
      Pedido pedido = pedidoOptional.get();
      pedido.addProducto(productoOptional.get());

      return ResponseEntity.ok(pedidoService.savePedido(pedido));
    }
    return ResponseEntity.notFound().build();
  }
  
  @DeleteMapping("/{id_pedido}/producto/{id_producto}")
  public ResponseEntity<?> removeProducto(@PathVariable(value = "id_pedido") Long idPedido, @PathVariable(value = "id_producto") Long idProducto){
    Optional<Producto> productoOptional = productoService.getProductoById(idProducto);
    Optional<Pedido> pedidoOptional = pedidoService.getPedidoById(idPedido);
    if (productoOptional.isPresent() && pedidoOptional.isPresent()){
      Pedido pedido = pedidoOptional.get();
      pedido.removeProducto(productoOptional.get());
      
      try{
        Pedido savedPedido = pedidoService.savePedido(pedido);
        return ResponseEntity.ok(savedPedido);
      } catch (PedidoInvalidoException e){
        return ResponseEntity.badRequest().body(e.getMessage());
      }
    }
    return ResponseEntity.notFound().build();
  }
}
