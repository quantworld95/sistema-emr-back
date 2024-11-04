package com.emr.sistema_emr_back.service;
/*
import com.commerce.ecommerce.model.Rol;
import com.commerce.ecommerce.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolService {
    @Autowired
    private RolRepository rolRepository;

    public List<Rol> findAll() {
        return rolRepository.findAll();
    }

    public Rol save(Rol rol) {
        return rolRepository.save(rol);
    }

    public Optional<Rol> findById(Long idRol) {
        return rolRepository.findById(idRol);
    }


    public void deleteById(Long id) {
        rolRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return rolRepository.existsById(id);
    }
}
*/



import com.emr.sistema_emr_back.entity.Permiso;
import com.emr.sistema_emr_back.repository.PermisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermisoService {

    @Autowired
    private PermisoRepository permisoRepository;

    // Obtener todos los permisos
    public List<Permiso> getAllPermisos() {
        return permisoRepository.findAll();
    }

    // Obtener un permiso por ID
    public Optional<Permiso> getPermisoById(Long id) {
        return permisoRepository.findById(id);
    }

    // Crear un nuevo permiso
    public Permiso createPermiso(Permiso permiso) {
        return permisoRepository.save(permiso);
    }

    // Actualizar un permiso existente
    public Permiso updatePermiso(Long id, Permiso permisoDetails) {
        Optional<Permiso> optionalPermiso = permisoRepository.findById(id);
        if (optionalPermiso.isPresent()) {
            Permiso permiso = optionalPermiso.get();
            permiso.setPermiso(permisoDetails.getPermiso());
            return permisoRepository.save(permiso);
        } else {
            throw new RuntimeException("Permiso no encontrado con id: " + id);
        }
    }

    // Eliminar un permiso por ID
    public void deletePermiso(Long id) {
        permisoRepository.deleteById(id);
    }
}