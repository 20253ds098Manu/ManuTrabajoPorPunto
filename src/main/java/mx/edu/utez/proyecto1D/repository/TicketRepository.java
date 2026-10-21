package mx.edu.utez.proyecto1D.repository;

import mx.edu.utez.proyecto1D.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByUsuarioId(Long usuarioId);
}
