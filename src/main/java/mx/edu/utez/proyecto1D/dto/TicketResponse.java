package mx.edu.utez.proyecto1D.dto;

import mx.edu.utez.proyecto1D.model.EstadoTicket;
import mx.edu.utez.proyecto1D.model.Prioridad;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponse {
    private Long id;
    private String descripcion;
    private EstadoTicket estado;
    private Prioridad prioridad;
    private Long usuarioId;
    private String nombreUsuario;
}
