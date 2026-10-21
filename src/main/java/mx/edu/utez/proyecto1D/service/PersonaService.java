package mx.edu.utez.proyecto1D.service;

import mx.edu.utez.proyecto1D.dto.ActualizarPersonaRequest;
import mx.edu.utez.proyecto1D.dto.PersonaResponse;
import mx.edu.utez.proyecto1D.exception.DatoDuplicadoException;
import mx.edu.utez.proyecto1D.exception.PersonaNoEncontradaException;
import mx.edu.utez.proyecto1D.model.Persona;
import mx.edu.utez.proyecto1D.repository.PersonaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonaService {

    private final PersonaRepository personaRepository;

    public PersonaService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    @Transactional(readOnly = true)
    public PersonaResponse obtenerPersona(Long id) {
        return aResponse(buscarPersona(id));
    }

    @Transactional
    public PersonaResponse actualizarPersona(Long id, ActualizarPersonaRequest request) {
        Persona persona = buscarPersona(id);

        String correo = request.getCorreo().trim();
        if (personaRepository.existsByCorreoIgnoreCaseAndIdNot(correo, id)) {
            throw new DatoDuplicadoException("El correo '" + correo + "' ya está registrado en otra persona");
        }

        persona.setNombre(request.getNombre().trim());
        persona.setApellidos(request.getApellidos().trim());
        persona.setCorreo(correo);
        persona.setTelefono(request.getTelefono());
        persona.setFechaNacimiento(request.getFechaNacimiento());

        return aResponse(personaRepository.save(persona));
    }

    private Persona buscarPersona(Long id) {
        return personaRepository.findById(id)
                .orElseThrow(() -> new PersonaNoEncontradaException(id));
    }

    static PersonaResponse aResponse(Persona p) {
        return new PersonaResponse(p.getId(), p.getNombre(), p.getApellidos(),
                p.getCorreo(), p.getTelefono(), p.getFechaNacimiento());
    }
}
