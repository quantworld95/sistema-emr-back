package com.emr.sistema_emr_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import com.emr.sistema_emr_back.entity.Permiso;
import com.emr.sistema_emr_back.entity.Rol;
import com.emr.sistema_emr_back.entity.RolPermiso;

import org.springframework.data.repository.query.Param;
@Repository
public interface RolPermisoRepository extends JpaRepository<RolPermiso, Long> {

    @Query("SELECT rp FROM RolPermiso rp WHERE rp.permiso.id = :idPermiso AND rp.rol.id = :idRol")
    Optional<RolPermiso> findByIdPermisoAndIdRol(@Param("idPermiso") Long idPermiso, @Param("idRol") Long idRol);

}
