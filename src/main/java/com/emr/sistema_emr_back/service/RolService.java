package com.emr.sistema_emr_back.service;
import com.emr.sistema_emr_back.entity.Rol;
import com.emr.sistema_emr_back.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    // Obtener todos los permisos
    public List<Rol> getAll() {
        return rolRepository.findAll();
    }

    // Obtener un permiso por ID
    public Optional<Rol> getRolById(Long id) {
        return rolRepository.findById(id);
    }

    // Crear un nuevo permiso
    public Rol createRol(Rol rol) {
        return rolRepository.save(rol);
    }

    // Actualizar un permiso existente
    public Rol updateRol(Long id, Rol rolDetails) {
        Optional<Rol> optionalRol = rolRepository.findById(id);
        if (optionalRol.isPresent()) {
            Rol rol = optionalRol.get();
          //  rol.setNombre(rolDetails.getRol());
            return rolRepository.save(rol);
        } else {
            throw new RuntimeException("Permiso no encontrado con id: " + id);
        }
    }

    // Eliminar un permiso por ID
    public void deleteRol(Long id) {
        rolRepository.deleteById(id);
    }
}