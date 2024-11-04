package com.emr.sistema_emr_back.controller;

import com.emr.sistema_emr_back.entity.Permiso;
import com.emr.sistema_emr_back.service.PermisoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/permiso")
public class PermisoController {

    @Autowired
    private PermisoService permisoService;

    // Obtener todos los permisos
    @GetMapping
    public ResponseEntity<List<Permiso>> getAll() {
        List<Permiso> permisos = permisoService.getAllPermisos();
        return ResponseEntity.ok(permisos);
    }

    // Obtener un permiso por ID
    @GetMapping("/{id}")
    public ResponseEntity<Permiso> getPermiso(@PathVariable Long id) {
        return permisoService.getPermisoById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    // Crear un nuevo permiso
    @PostMapping
    public ResponseEntity<Permiso> store(@RequestBody Permiso permiso) {
        try {
            Permiso nuevoPermiso = permisoService.createPermiso(permiso);
            URI location = new URI("/permiso/crear/" + nuevoPermiso.getId());
            return ResponseEntity.created(location).body(nuevoPermiso);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Actualizar un permiso existente
    @PutMapping("/{id}")
    public ResponseEntity<Permiso> update(@PathVariable Long id, @RequestBody Permiso permisoActualizado) {
        try {
            Optional<Permiso> permisoExistente = permisoService.getPermisoById(id);

            if (permisoExistente.isPresent()) {
                permisoActualizado.setId(id);  // Asegurarse de mantener el mismo ID
                Permiso updatedPermiso = permisoService.updatePermiso(id, permisoActualizado);
                return ResponseEntity.ok(updatedPermiso);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Eliminar un permiso por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            if (permisoService.getPermisoById(id).isPresent()) {
                permisoService.deletePermiso(id);
                return ResponseEntity.ok().build(); // Respuesta 200 OK
            } else {
                return ResponseEntity.notFound().build(); // Respuesta 404 Not Found si no existe
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Respuesta 500 si ocurre algún error inesperado
        }
    }
}
