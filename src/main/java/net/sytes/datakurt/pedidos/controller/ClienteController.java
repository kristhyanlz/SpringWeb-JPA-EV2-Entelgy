package net.sytes.datakurt.pedidos.controller;

import lombok.RequiredArgsConstructor;
import net.sytes.datakurt.pedidos.entity.Cliente;
import net.sytes.datakurt.pedidos.entity.Pedido;
import net.sytes.datakurt.pedidos.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {
  
  private final ClienteService clienteService;
  
  @GetMapping
  public List<Cliente> getClientes(){
    return clienteService.findAll();
  }
  
  @GetMapping("/{id}")
  public ResponseEntity<Cliente> getCliente(@PathVariable("id") Long id){
    Optional<Cliente> cliente = clienteService.getClienteById(id);
    if (cliente.isPresent()){
      return ResponseEntity.ok(cliente.get());
    }
    return ResponseEntity.notFound().build();
  }
  
  @PostMapping
  public Cliente createCliente(@RequestBody Cliente cliente) {
    return clienteService.saveCliente(cliente);
  }
  
  @PutMapping("{id}")
  public ResponseEntity<Cliente> updateCliente(@PathVariable Long id, @RequestBody Cliente clienteDetails){
    Optional<Cliente> clienteOptional = clienteService.getClienteById(id);
    if (clienteOptional.isPresent()){
      Cliente updatedCliente = clienteOptional.get();
      updatedCliente.setNombreCliente(clienteDetails.getNombreCliente());
      updatedCliente.setDi(clienteDetails.getDi());
      
      //Limpiamos los pedidos para asegurar la asociacion
      updatedCliente.getPedidos().clear();
      //Volvemos a agregar a todos los pedidos
      for (Pedido pedido: clienteDetails.getPedidos()){
        updatedCliente.addPedido(pedido);
      }
      return ResponseEntity.ok(clienteService.saveCliente(updatedCliente));
    }
    return ResponseEntity.notFound().build();
  }
  
  @DeleteMapping("{id}")
  public ResponseEntity<Void> deleteCliente(@PathVariable Long id){
    clienteService.deleteCliente(id);
    return ResponseEntity.noContent().build();
  }
}
