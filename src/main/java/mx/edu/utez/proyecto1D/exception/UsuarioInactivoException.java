package mx.edu.utez.proyecto1D.exception;

public class UsuarioInactivoException extends RuntimeException {
    public UsuarioInactivoException(Long id) {
        super("El usuario con id " + id + " está inactivo y no puede crear tickets");
    }
}
