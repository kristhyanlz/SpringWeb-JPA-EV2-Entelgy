package net.sytes.datakurt.pedidos.repository;

import net.sytes.datakurt.pedidos.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
