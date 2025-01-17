package net.sytes.datakurt.pedidos.service.exceptions;

public class PedidoInvalidoException extends RuntimeException{
  private static final String DEFAULT_MESSAGE = "No se permiten pedidos con menos de 3 productos.";
  
  public PedidoInvalidoException(){
    super(DEFAULT_MESSAGE);
  }
  
  public PedidoInvalidoException(String message) {
    super(message);
  }
}
