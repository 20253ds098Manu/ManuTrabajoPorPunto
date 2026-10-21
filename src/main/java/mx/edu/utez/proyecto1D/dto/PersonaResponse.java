package mx.edu.utez.proyecto1D.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonaResponse {
    private Long id;
    private String nombre;
    private String apellidos;
    private String correo;
    private String telefono;
    private LocalDate fechaNacimiento;
}
