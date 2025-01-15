package net.sytes.datakurt.pedidos.service;

import lombok.RequiredArgsConstructor;
import net.sytes.datakurt.pedidos.entity.Producto;
import net.sytes.datakurt.pedidos.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoService {
  
  private final ProductoRepository productoRepository;
  
  public List<Producto> findAll(){
    return productoRepository.findAll();
  }
  
  public Optional<Producto> getProductoById(Long id){
    return productoRepository.findById(id);
  }
  
  public Producto saveProducto(Producto producto) {
    return productoRepository.save(producto);
  }
  
  public void deleteProducto(Long id) {
    productoRepository.deleteById(id);
  }
}
