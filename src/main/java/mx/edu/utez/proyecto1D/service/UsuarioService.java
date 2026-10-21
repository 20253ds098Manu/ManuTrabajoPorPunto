package mx.edu.utez.proyecto1D.service;

import mx.edu.utez.proyecto1D.dto.ActualizarUsuarioRequest;
import mx.edu.utez.proyecto1D.dto.UsuarioResponse;
import mx.edu.utez.proyecto1D.exception.DatoDuplicadoException;
import mx.edu.utez.proyecto1D.exception.UsuarioNoEncontradoException;
import mx.edu.utez.proyecto1D.model.Usuario;
import mx.edu.utez.proyecto1D.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public UsuarioResponse obtenerUsuario(Long id) {
        return aResponse(buscarUsuario(id));
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponse> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::aResponse)
                .toList();
    }

    @Transactional
    public UsuarioResponse activarUsuario(Long id) {
        return cambiarEstado(id, true);
    }

    @Transactional
    public UsuarioResponse desactivarUsuario(Long id) {
        return cambiarEstado(id, false);
    }

    @Transactional
    public UsuarioResponse actualizarUsuario(Long id, ActualizarUsuarioRequest request) {
        Usuario usuario = buscarUsuario(id);

        String username = request.getUsername().trim();
        if (usuarioRepository.existsByUsernameIgnoreCaseAndIdNot(username, id)) {
            throw new DatoDuplicadoException("El username '" + username + "' ya está en uso");
        }

        usuario.setUsername(username);
        String descripcion = request.getDescripcion();
        usuario.setDescripcion(descripcion == null || descripcion.isBlank() ? null : descripcion.trim());

        return aResponse(usuarioRepository.save(usuario));
    }

    private UsuarioResponse cambiarEstado(Long id, boolean activo) {
        Usuario usuario = buscarUsuario(id);
        usuario.setActivo(activo);
        return aResponse(usuarioRepository.save(usuario));
    }

    private Usuario buscarUsuario(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException(id));
    }

    private UsuarioResponse aResponse(Usuario u) {
        return new UsuarioResponse(u.getId(), u.getUsername(), u.getDescripcion(), u.isActivo(),
                PersonaService.aResponse(u.getPersona()));
    }
}
