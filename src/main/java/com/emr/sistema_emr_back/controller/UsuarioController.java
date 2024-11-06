package com.emr.sistema_emr_back.controller;

import com.emr.sistema_emr_back.DTO.UsuarioCrearDTO;
import com.emr.sistema_emr_back.entity.Usuario;
import com.emr.sistema_emr_back.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioService.getAllUsuarios().stream()
                .collect(Collectors.toList()); // Eliminar el `.map` si solo quieres la lista completa
    }

    @GetMapping("/{username}")
    public Usuario getUsuarioByUsername(@PathVariable String username) {
        return usuarioService.usuarioByUsername(username);
    }

    @PostMapping
    public Usuario createUsuario(@RequestBody UsuarioCrearDTO usuarioCrearDTO) {
        return usuarioService.store(usuarioCrearDTO);
    }

    @PutMapping("/{id}")
    public Usuario updateUsuario(@PathVariable Long id, @RequestBody UsuarioCrearDTO usuarioActualizado) {
        return usuarioService.update(id, usuarioActualizado);
    }

    @DeleteMapping("/{id}")
    public void deleteUsuario(@PathVariable Long id) {
        usuarioService.delete(id);
    }
}
