package com.emr.sistema_emr_back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;



import com.emr.sistema_emr_back.entity.Rol;
import com.emr.sistema_emr_back.service.RolService;
/*
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

        import java.net.URI;
import java.util.List;
import java.util.Optional;
*/

//@PreAuthorize("hasRole('rol')")
@RestController
@RequestMapping("/rol")
public class RolController {
    @Autowired
    private RolService rolService;

    @GetMapping
    private ResponseEntity<List<Rol>> getAllAsistencia(){
        return ResponseEntity.ok(rolService.getAll());
    }

    @GetMapping("/{id}")
    private ResponseEntity<Rol> getRol(@PathVariable Long id){
        try{
            Optional<Rol> rol = rolService.getRolById(id);
            return ResponseEntity.ok(rol.get());
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    private ResponseEntity<Rol> store(@RequestBody Rol rol){
        try {
            Rol nuevo = rolService.createRol(rol);
            return ResponseEntity.created(new URI("/rol/crear/"+nuevo.getId())).body(nuevo);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {

                rolService.deleteRol(id); // Elimina el usuario si existe
                return ResponseEntity.ok().build(); // Respuesta 200 OK

        } catch (Exception e) {
            return ResponseEntity.status(500).build(); // Respuesta 500 si ocurre algún error inesperado
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rol> update(@PathVariable Long id, @RequestBody Rol rolActualizado) {
        try {
                Rol rol = rolService.updateRol(id, rolActualizado);
                return ResponseEntity.ok(rol); // Retorna el rol actualizado con 200 OK

        } catch (Exception e) {
            return ResponseEntity.status(500).build(); // Retorna 500 si hay un error
        }
    }


}
