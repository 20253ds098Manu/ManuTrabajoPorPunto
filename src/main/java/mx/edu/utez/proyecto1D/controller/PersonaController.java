package mx.edu.utez.proyecto1D.controller;

import jakarta.validation.Valid;
import mx.edu.utez.proyecto1D.dto.ActualizarPersonaRequest;
import mx.edu.utez.proyecto1D.dto.PersonaResponse;
import mx.edu.utez.proyecto1D.service.PersonaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/personas")
public class PersonaController {

    private final PersonaService personaService;

    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonaResponse> obtenerPersona(@PathVariable("id") Long id) {
        return ResponseEntity.ok(personaService.obtenerPersona(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonaResponse> actualizarPersona(@PathVariable("id") Long id,
                                                             @Valid @RequestBody ActualizarPersonaRequest request) {
        return ResponseEntity.ok(personaService.actualizarPersona(id, request));
    }
}
