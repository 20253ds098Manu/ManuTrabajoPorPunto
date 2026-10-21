package mx.edu.utez.proyecto1D.controller;

import jakarta.validation.Valid;
import mx.edu.utez.proyecto1D.dto.ActualizarUsuarioRequest;
import mx.edu.utez.proyecto1D.dto.TicketResponse;
import mx.edu.utez.proyecto1D.dto.UsuarioResponse;
import mx.edu.utez.proyecto1D.service.TicketService;
import mx.edu.utez.proyecto1D.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final TicketService ticketService;

    public UsuarioController(UsuarioService usuarioService, TicketService ticketService) {
        this.usuarioService = usuarioService;
        this.ticketService = ticketService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> obtenerUsuario(@PathVariable("id") Long id) {
        return ResponseEntity.ok(usuarioService.obtenerUsuario(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> actualizarUsuario(@PathVariable("id") Long id,
                                                             @Valid @RequestBody ActualizarUsuarioRequest request) {
        return ResponseEntity.ok(usuarioService.actualizarUsuario(id, request));
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<UsuarioResponse> activarUsuario(@PathVariable("id") Long id) {
        return ResponseEntity.ok(usuarioService.activarUsuario(id));
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<UsuarioResponse> desactivarUsuario(@PathVariable("id") Long id) {
        return ResponseEntity.ok(usuarioService.desactivarUsuario(id));
    }

    @GetMapping("/{id}/tickets")
    public ResponseEntity<List<TicketResponse>> obtenerTicketsUsuario(@PathVariable("id") Long id) {
        return ResponseEntity.ok(ticketService.obtenerTicketsUsuario(id));
    }
}
