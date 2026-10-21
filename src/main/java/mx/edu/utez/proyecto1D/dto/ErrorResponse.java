package mx.edu.utez.proyecto1D.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final String mensaje;
    private final String path;
    private final Map<String, String> errores;

    public ErrorResponse(int status, String error, String mensaje, String path, Map<String, String> errores) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.mensaje = mensaje;
        this.path = path;
        this.errores = errores;
    }
}
