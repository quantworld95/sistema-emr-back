package com.emr.sistema_emr_back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import com.emr.sistema_emr_back.DTO.UsuarioCrearDTO;

import com.emr.sistema_emr_back.entity.Usuario;
import com.emr.sistema_emr_back.service.RolService;
import com.emr.sistema_emr_back.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    private ResponseEntity<List<Usuario>> getAll(){
        return ResponseEntity.ok(usuarioService.getAllusuario());
    }

    @GetMapping("/by/{user}")
    private  ResponseEntity<Usuario> usuarioByUsername(@PathVariable String user){
        try {
            Usuario buscado = usuarioService.usuarioByUsername(user);
            return ResponseEntity.ok(buscado);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }

    @PostMapping
    private ResponseEntity<Usuario> store(@RequestBody UsuarioCrearDTO c){
        try {
            Usuario usuario = usuarioService.store(c);


            return ResponseEntity.ok(usuario);


        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}")
    private ResponseEntity<Usuario> get(@PathVariable Long id){
        try{
            Usuario usuario = usuarioService.get(id);
            return ResponseEntity.ok(usuario);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            usuarioService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Usuario> update(@PathVariable Long id, @RequestBody UsuarioCrearDTO usuarioActualizado) {
        try {
            Usuario usuario = usuarioService.update(id, usuarioActualizado);
                return ResponseEntity.ok(usuario); // Retorna el rol actualizado con 200 OK

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }
}