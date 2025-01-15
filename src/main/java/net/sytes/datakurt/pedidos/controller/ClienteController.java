package net.sytes.datakurt.pedidos.controller;

import lombok.RequiredArgsConstructor;
import net.sytes.datakurt.pedidos.entity.Cliente;
import net.sytes.datakurt.pedidos.service.ClienteService;
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
  public Optional<Cliente> getCliente(@PathVariable("id") Long id){
    return clienteService.getClienteById(id);
  }
  
  @PostMapping
  public Cliente createCliente(@RequestBody Cliente cliente) {
    return clienteService.saveCliente(cliente);
  }
}
