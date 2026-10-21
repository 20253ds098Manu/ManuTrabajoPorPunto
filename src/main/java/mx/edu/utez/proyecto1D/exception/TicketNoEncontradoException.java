package mx.edu.utez.proyecto1D.exception;

public class TicketNoEncontradoException extends RuntimeException {
    public TicketNoEncontradoException(Long id) {
        super("No se encontró el ticket con id " + id);
    }
}
