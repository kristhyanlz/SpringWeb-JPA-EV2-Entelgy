package net.sytes.datakurt.pedidos.service;

import lombok.RequiredArgsConstructor;
import net.sytes.datakurt.pedidos.entity.Cliente;
import net.sytes.datakurt.pedidos.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {
  
  private final ClienteRepository clienteRepository;
  
  public List<Cliente> findAll(){
    return clienteRepository.findAll();
  }
  
  public Optional<Cliente> getClienteById(Long id){
    return clienteRepository.findById(id);
  }
  
  public Cliente saveCliente(Cliente cliente) {
    return clienteRepository.save(cliente);
  }
  
  public void deleteCliente(Long id) {
    clienteRepository.deleteById(id);
  }
}
