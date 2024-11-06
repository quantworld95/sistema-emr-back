package com.emr.sistema_emr_back.repository;

import com.emr.sistema_emr_back.entity.Asegurado;
import com.emr.sistema_emr_back.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AseguradoRepository extends JpaRepository<Asegurado, Long> {
    // Puedes agregar métodos personalizados aquí si los necesitas
}
