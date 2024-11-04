package com.emr.sistema_emr_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.emr.sistema_emr_back.entity.Permiso;

@Repository
public interface PermisoRepository extends JpaRepository<Permiso, Long> {
}
