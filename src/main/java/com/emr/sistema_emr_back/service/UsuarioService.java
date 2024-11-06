package com.emr.sistema_emr_back.service;

import com.emr.sistema_emr_back.DTO.UsuarioCrearDTO;
import com.emr.sistema_emr_back.entity.Rol;
import com.emr.sistema_emr_back.entity.Usuario;
import com.emr.sistema_emr_back.repository.RolRepository;
import com.emr.sistema_emr_back.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario usuarioByUsername(String user) {
        return usuarioRepository.findByUsername(user)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con username: " + user));
    }

    public Usuario store(UsuarioCrearDTO c) {
        Rol rol = rolRepository.findById(c.getId_rol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con id: " + c.getId_rol()));

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setEmail(c.getEmail());
        nuevoUsuario.setUsername(c.getUsername());
        nuevoUsuario.setPassword(passwordEncoder.encode(c.getPassword()));
        nuevoUsuario.setRol(rol);

        return usuarioRepository.save(nuevoUsuario);
    }

    public Usuario get(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }

    public void delete(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con id: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    public Usuario update(Long id, UsuarioCrearDTO usuarioActualizado) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        Rol rol = rolRepository.findById(usuarioActualizado.getId_rol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con id: " + usuarioActualizado.getId_rol()));

        usuario.setUsername(usuarioActualizado.getUsername());
        usuario.setEmail(usuarioActualizado.getEmail());

        if (!usuarioActualizado.getPassword().isEmpty() &&
                !passwordEncoder.matches(usuarioActualizado.getPassword(), usuario.getPassword())) {
            usuario.setPassword(passwordEncoder.encode(usuarioActualizado.getPassword()));
        }

        usuario.setRol(rol);
        return usuarioRepository.save(usuario);
    }
}
