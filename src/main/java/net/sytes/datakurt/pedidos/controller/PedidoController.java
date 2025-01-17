package net.sytes.datakurt.pedidos.controller;

import lombok.RequiredArgsConstructor;
import net.sytes.datakurt.pedidos.entity.Pedido;
import net.sytes.datakurt.pedidos.entity.Producto;
import net.sytes.datakurt.pedidos.service.PedidoService;
import net.sytes.datakurt.pedidos.service.ProductoService;
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
  
  @GetMapping
  public List<Pedido> getPedidos(){
    return pedidoService.findAll();
  }
  
  @GetMapping("/{id}")
  public Optional<Pedido> getPedido(@PathVariable("id") Long id){
    return pedidoService.getPedidoById(id);
  }
  
  @PostMapping
  public Pedido createPedido(@RequestBody Pedido pedido) {
    return pedidoService.savePedido(pedido);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Pedido> updatePedido(@PathVariable Long id, @RequestBody Pedido pedidoDetails){
    Optional<Pedido> pedidoOptional = pedidoService.getPedidoById(id);
    if (pedidoOptional.isPresent()){
      Pedido updatedPedido = pedidoOptional.get();
      updatedPedido.setFechaCompra(pedidoDetails.getFechaCompra());
      //Esto se debería calcular
      updatedPedido.setCostoTotal(pedidoDetails.getCostoTotal());
      updatedPedido.setDireccionEnvio(pedidoDetails.getDireccionEnvio());
      //La FK se pasa normal
      updatedPedido.setCliente(pedidoDetails.getCliente());
      
      //Limpiamos los productos para asegurar la asociacion
      updatedPedido.getProductos().clear();
      //Volvemos a agregar a todos los productos
      for(Producto producto : pedidoDetails.getProductos()){
        updatedPedido.addProducto(producto);
      }
      
      return ResponseEntity.ok(pedidoService.savePedido(updatedPedido));
    }
    return ResponseEntity.notFound().build();
  }
  
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePedido(@PathVariable Long id){
    pedidoService.deletePedido(id);
    return ResponseEntity.noContent().build();
  }
  
  @PostMapping("/addProducto/{id}")
  public ResponseEntity<Pedido> addProducto(@PathVariable Long id, @RequestBody Producto reqProducto){
    Optional<Producto> productoOptional = productoService.getProductoById(reqProducto.getIdProducto());
    Optional<Pedido> pedidoOptional = pedidoService.getPedidoById(id);
    if (productoOptional.isPresent() && pedidoOptional.isPresent()){
      Pedido pedido = pedidoOptional.get();
      pedido.addProducto(productoOptional.get());
      return ResponseEntity.ok(pedidoService.savePedido(pedido));
    }
    return ResponseEntity.notFound().build();
  }
}
