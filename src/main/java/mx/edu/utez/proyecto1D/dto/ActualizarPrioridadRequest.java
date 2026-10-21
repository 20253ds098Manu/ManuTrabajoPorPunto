package mx.edu.utez.proyecto1D.dto;

import mx.edu.utez.proyecto1D.model.Prioridad;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarPrioridadRequest {

    @NotNull(message = "La prioridad es obligatoria")
    private Prioridad prioridad;
}
