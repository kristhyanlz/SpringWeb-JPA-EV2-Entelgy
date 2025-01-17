package net.sytes.datakurt.pedidos.controller;

import lombok.RequiredArgsConstructor;
import net.sytes.datakurt.pedidos.entity.Producto;
import net.sytes.datakurt.pedidos.service.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {
  
  private final ProductoService productoService;
  
  @GetMapping
  public List<Producto> getProductos(){
    return productoService.findAll();
  }
  
  @GetMapping("/{id}")
  public ResponseEntity<Producto> getProductoById(@PathVariable Long id) {
    Optional<Producto> producto = productoService.getProductoById(id);
    if (producto.isPresent()) {
      return ResponseEntity.ok(producto.get());
    } else {
      return ResponseEntity.notFound().build();
    }
  }
  
  @PostMapping
  public Producto createProducto(@RequestBody Producto producto) {
    return productoService.saveProducto(producto);
  }
  
  @PutMapping("/{id}")
  public ResponseEntity<Producto> updateProducto(@PathVariable Long id, @RequestBody Producto productoDetails){
    Optional<Producto> productoOptional = productoService.getProductoById(id);
    
    if (productoOptional.isPresent()){
      Producto updatedProducto = productoOptional.get();
      updatedProducto.setDescripcionProducto(productoDetails.getDescripcionProducto());
      updatedProducto.setMarcaProducto(productoDetails.getMarcaProducto());
      updatedProducto.setNombreProducto(productoDetails.getNombreProducto());
      updatedProducto.setPrecioProducto(productoDetails.getPrecioProducto());
      
      /*
      //Limpiamos los pedidos para asegurar la asociacion
      updatedProducto.getPedidos().clear();
      //Volvemos a agregar a todos los pedidos
      for(Pedido pedido : productoDetails.getPedidos()){
        updatedProducto.addPedido(pedido);
      }
       */
      //En pro de la integridad de los datos no se podrá modificar los pedidos
      //updatedProducto.setPedidos(productoDetails.getPedidos());
      return ResponseEntity.ok(productoService.saveProducto(updatedProducto));
    }
    return ResponseEntity.notFound().build();
  }
  
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProducto(@PathVariable Long id){
    productoService.deleteProducto(id);
    return ResponseEntity.noContent().build();
  }
}
