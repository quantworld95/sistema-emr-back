package com.emr.sistema_emr_back.service;

import com.emr.sistema_emr_back.repository.RolRepository;
import com.emr.sistema_emr_back.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

import com.emr.sistema_emr_back.DTO.UsuarioCrearDTO;

import com.emr.sistema_emr_back.entity.Rol;
import com.emr.sistema_emr_back.entity.Usuario;




import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public List<Usuario> getAllusuario() {
        return usuarioRepository.findAll();
    }


    public Usuario usuarioByUsername(String user) {

        Optional<Usuario> buscado = usuarioRepository.findByUsername(user);
        return buscado.get();
    }


    public Usuario store(UsuarioCrearDTO c) {

        Optional<Rol> rol = rolRepository.findById(c.getId_rol());

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setEmail(c.getEmail());
        nuevoUsuario.setUsername(c.getUsername());
      nuevoUsuario.setPassword(passwordEncoder.encode(c.getPassword()));

        nuevoUsuario.setRol(rol.get());  // Asigna el rol encontrado

        Usuario nuevo = usuarioRepository.save(nuevoUsuario);
        return nuevo;
    }


    public Usuario get(Long id) {

        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.get();
    }


    public void delete(Long id) {

        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
        } else {
            throw new RuntimeException("usuario no encontrado con id: " + id);
        }

    }


    public Usuario update(Long id, UsuarioCrearDTO usuarioActualizado) {

        Optional<Usuario> existente = usuarioRepository.findById(id);

        if (existente.isPresent()) {
            Optional<Rol> rol = rolRepository.findById(usuarioActualizado.getId_rol());
            Usuario usuario = existente.get();
            usuario.setUsername(usuarioActualizado.getUsername());
            usuario.setEmail(usuarioActualizado.getEmail());

         //   usuario.setPassword(usuarioActualizado.getPassword());


            // Si la contraseña en el DTO es diferente a la almacenada, se encripta y actualiza
            if (!passwordEncoder.matches(usuarioActualizado.getPassword(), usuario.getPassword())) {
                usuario.setPassword(passwordEncoder.encode(usuarioActualizado.getPassword()));
            }
            usuario.setRol(rol.get());

            // Guardar el rol actualizado
            return usuarioRepository.save(usuario);

        } else {
            throw new RuntimeException("usuario no encontrado con id: " + id);
        }

    }
}