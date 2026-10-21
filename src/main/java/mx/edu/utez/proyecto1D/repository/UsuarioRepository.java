package mx.edu.utez.proyecto1D.repository;

import mx.edu.utez.proyecto1D.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByUsernameIgnoreCaseAndIdNot(String username, Long id);
}
