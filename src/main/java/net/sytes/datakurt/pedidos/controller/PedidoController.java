package net.sytes.datakurt.pedidos.controller;

import lombok.RequiredArgsConstructor;
import net.sytes.datakurt.pedidos.entity.Pedido;
import net.sytes.datakurt.pedidos.service.PedidoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {
  private final PedidoService pedidoService;
  
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
}
