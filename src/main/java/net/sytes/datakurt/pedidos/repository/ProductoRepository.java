package net.sytes.datakurt.pedidos.repository;

import net.sytes.datakurt.pedidos.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
