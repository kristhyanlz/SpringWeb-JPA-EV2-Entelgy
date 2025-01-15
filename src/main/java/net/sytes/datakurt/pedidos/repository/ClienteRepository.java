package net.sytes.datakurt.pedidos.repository;

import net.sytes.datakurt.pedidos.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}