package com.emr.sistema_emr_back.controller;

import com.emr.sistema_emr_back.service.AseguradoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AseguradoController {

    @Autowired
    private AseguradoService aseguradoService;

    @PostMapping("/api/asegurados/upload")
    public ResponseEntity<String> uploadExcel(@RequestParam("file") MultipartFile file) {
        try {
            aseguradoService.procesarExcel(file);
            return ResponseEntity.ok("Asegurados procesados correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al procesar el archivo");
        }
    }
}
