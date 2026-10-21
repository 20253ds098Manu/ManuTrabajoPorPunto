package mx.edu.utez.proyecto1D.repository;

import mx.edu.utez.proyecto1D.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<Persona, Long> {

    boolean existsByCorreoIgnoreCaseAndIdNot(String correo, Long id);
}
