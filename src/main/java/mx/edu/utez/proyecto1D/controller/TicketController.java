package mx.edu.utez.proyecto1D.controller;

import mx.edu.utez.proyecto1D.dto.ActualizarPrioridadRequest;
import mx.edu.utez.proyecto1D.dto.CrearTicketRequest;
import mx.edu.utez.proyecto1D.dto.TicketResponse;
import mx.edu.utez.proyecto1D.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<TicketResponse> crearTicket(@Valid @RequestBody CrearTicketRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.crearTicket(request));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<TicketResponse> actualizarEstadoTicket(@PathVariable("id") Long id) {
        return ResponseEntity.ok(ticketService.actualizarEstadoTicket(id));
    }

    @PatchMapping("/{id}/prioridad")
    public ResponseEntity<TicketResponse> reasignarPrioridad(@PathVariable("id") Long id,
                                                             @Valid @RequestBody ActualizarPrioridadRequest request) {
        return ResponseEntity.ok(ticketService.reasignarPrioridad(id, request));
    }
}
