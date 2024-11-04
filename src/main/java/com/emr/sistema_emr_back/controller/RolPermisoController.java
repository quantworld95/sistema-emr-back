package com.emr.sistema_emr_back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.emr.sistema_emr_back.DTO.RolPermisoDTO;
import com.emr.sistema_emr_back.entity.*;
import com.emr.sistema_emr_back.entity.RolPermiso;
import com.emr.sistema_emr_back.service.*;
//import com.emr.sistema_emr_back.service.RolPermisoService;
// com.emr.sistema_emr_back.service.RolService;

import com.emr.sistema_emr_back.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rol_permiso")
public class RolPermisoController {
    @Autowired
    private RolPermisoService rolPermisoService;

    @Autowired
    private RolService rolService;

    @Autowired
    private UsuarioService usuarioService;



    @PostMapping
    public ResponseEntity<List<RolPermiso>> store(@RequestBody List<RolPermisoDTO> lista){
        try {

            List<RolPermiso> nuevaLista = rolPermisoService.store(lista);

            return ResponseEntity.ok(nuevaLista);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(path = "/{id}")
    private ResponseEntity<List<Permiso>> getlistaPermiso(@PathVariable Long id){
        try{

            List<Permiso> todos = rolPermisoService.getList(id);

            return ResponseEntity.ok(todos);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }


}
