package mx.edu.utez.proyecto1D.exception;

public class UsuarioNoEncontradoException extends RuntimeException {
    public UsuarioNoEncontradoException(Long id) {
        super("No se encontró el usuario con id " + id);
    }
}
