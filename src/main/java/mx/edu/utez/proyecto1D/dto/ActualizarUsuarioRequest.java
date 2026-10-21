package mx.edu.utez.proyecto1D.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarUsuarioRequest {

    @NotBlank(message = "El username es obligatorio")
    @Size(min = 3, max = 50, message = "El username debe tener entre 3 y 50 caracteres")
    @Pattern(regexp = "^[A-Za-z0-9._-]*$",
            message = "El username solo puede contener letras, números, punto, guion y guion bajo")
    private String username;

    @Size(max = 255, message = "La descripción no puede exceder 255 caracteres")
    private String descripcion;
}
