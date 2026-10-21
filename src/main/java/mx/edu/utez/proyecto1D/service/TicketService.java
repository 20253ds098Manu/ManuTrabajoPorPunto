package mx.edu.utez.proyecto1D.service;

import mx.edu.utez.proyecto1D.dto.ActualizarPrioridadRequest;
import mx.edu.utez.proyecto1D.dto.CrearTicketRequest;
import mx.edu.utez.proyecto1D.dto.TicketResponse;
import mx.edu.utez.proyecto1D.exception.CustomBadRequestException;
import mx.edu.utez.proyecto1D.exception.TicketCerradoException;
import mx.edu.utez.proyecto1D.exception.TicketNoEncontradoException;
import mx.edu.utez.proyecto1D.exception.UsuarioInactivoException;
import mx.edu.utez.proyecto1D.exception.UsuarioNoEncontradoException;
import mx.edu.utez.proyecto1D.model.EstadoTicket;
import mx.edu.utez.proyecto1D.model.Ticket;
import mx.edu.utez.proyecto1D.model.Usuario;
import mx.edu.utez.proyecto1D.repository.TicketRepository;
import mx.edu.utez.proyecto1D.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UsuarioRepository usuarioRepository;

    public TicketService(TicketRepository ticketRepository, UsuarioRepository usuarioRepository) {
        this.ticketRepository = ticketRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public TicketResponse crearTicket(CrearTicketRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new UsuarioNoEncontradoException(request.getUsuarioId()));

        if (!usuario.isActivo()) {
            throw new UsuarioInactivoException(usuario.getId());
        }

        if (request.getDescripcion() == null || request.getDescripcion().isBlank()) {
            throw new CustomBadRequestException("La descripción no puede estar vacía");
        }

        Ticket ticket = new Ticket();
        ticket.setDescripcion(request.getDescripcion().trim());
        ticket.setEstado(EstadoTicket.ABIERTO); // siempre inicia ABIERTO
        ticket.setPrioridad(request.getPrioridad());
        ticket.setUsuario(usuario);

        return aResponse(ticketRepository.save(ticket));
    }

    @Transactional
    public TicketResponse actualizarEstadoTicket(Long id) {
        Ticket ticket = buscarTicket(id);

        EstadoTicket siguiente = switch (ticket.getEstado()) {
            case ABIERTO -> EstadoTicket.EN_PROCESO;
            case EN_PROCESO -> EstadoTicket.CERRADO;
            case CERRADO -> throw new TicketCerradoException(id);
        };

        ticket.setEstado(siguiente);
        return aResponse(ticketRepository.save(ticket));
    }

    @Transactional
    public TicketResponse reasignarPrioridad(Long id, ActualizarPrioridadRequest request) {
        Ticket ticket = buscarTicket(id);

        if (ticket.getEstado() == EstadoTicket.CERRADO) {
            throw new TicketCerradoException(id);
        }

        ticket.setPrioridad(request.getPrioridad());
        return aResponse(ticketRepository.save(ticket));
    }

    @Transactional(readOnly = true)
    public List<TicketResponse> obtenerTicketsUsuario(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new UsuarioNoEncontradoException(usuarioId);
        }
        return ticketRepository.findByUsuarioId(usuarioId).stream()
                .map(this::aResponse)
                .toList();
    }

    private Ticket buscarTicket(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNoEncontradoException(id));
    }

    private TicketResponse aResponse(Ticket t) {
        return new TicketResponse(
                t.getId(),
                t.getDescripcion(),
                t.getEstado(),
                t.getPrioridad(),
                t.getUsuario().getId(),
                t.getUsuario().getUsername());
    }
}
