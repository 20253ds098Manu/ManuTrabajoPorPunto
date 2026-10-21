package mx.edu.utez.proyecto1D.exception;

public class TicketCerradoException extends RuntimeException {
    public TicketCerradoException(Long id) {
        super("El ticket con id " + id + " está CERRADO y no puede modificarse");
    }
}
