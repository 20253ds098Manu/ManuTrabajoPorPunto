package mx.edu.utez.proyecto1D.config;

import mx.edu.utez.proyecto1D.model.Persona;
import mx.edu.utez.proyecto1D.model.Usuario;
import mx.edu.utez.proyecto1D.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;

    public DataLoader(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(String... args) {
        if (usuarioRepository.count() == 0) {
            usuarioRepository.saveAll(List.of(
                    new Usuario("ana.garcia", "Soporte nivel 1", true,
                            new Persona("Ana", "García López", "ana.garcia@utez.edu.mx", "7771234567", LocalDate.of(2000, 5, 14))),
                    new Usuario("luis.hernandez", "Administrador de redes", true,
                            new Persona("Luis", "Hernández Ruiz", "luis.hernandez@utez.edu.mx", "7779876543", LocalDate.of(1998, 11, 3))),
                    new Usuario("carlos.ramirez", "Cuenta suspendida", false,
                            new Persona("Carlos", "Ramírez Soto", "carlos.ramirez@utez.edu.mx", null, null))
            ));
        }
    }
}
