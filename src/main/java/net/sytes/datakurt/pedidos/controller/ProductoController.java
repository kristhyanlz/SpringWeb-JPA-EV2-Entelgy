package net.sytes.datakurt.pedidos.controller;

import lombok.RequiredArgsConstructor;
import net.sytes.datakurt.pedidos.entity.Producto;
import net.sytes.datakurt.pedidos.service.ProductoService;
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
  public Optional<Producto> getProducto(@PathVariable("id") Long id){
    return productoService.getProductoById(id);
  }
  
  @PostMapping
  public Producto createProducto(@RequestBody Producto producto) {
    return productoService.saveProducto(producto);
  }
}
