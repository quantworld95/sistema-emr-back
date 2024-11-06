package com.emr.sistema_emr_back.service;

import com.emr.sistema_emr_back.entity.Asegurado;
import com.emr.sistema_emr_back.entity.Usuario;
import com.emr.sistema_emr_back.repository.AseguradoRepository;
import com.emr.sistema_emr_back.repository.UsuarioRepository;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AseguradoService {

    @Autowired
    private AseguradoRepository aseguradoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmailService emailService;

    public void procesarExcel(MultipartFile file) throws IOException {
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        List<Asegurado> nuevosAsegurados = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            Asegurado asegurado = new Asegurado();
            asegurado.setNombre(row.getCell(0).getStringCellValue());
            asegurado.setApellidoPaterno(row.getCell(1).getStringCellValue());
            asegurado.setApellidoMaterno(row.getCell(2).getStringCellValue());
            asegurado.setCi(row.getCell(3).getStringCellValue());

            if (row.getCell(4) != null) {
                asegurado.setComplemento(row.getCell(4).getStringCellValue());
            } else {
                asegurado.setComplemento("");
            }

            if (DateUtil.isCellDateFormatted(row.getCell(5))) {
                asegurado.setFechaNacimiento(row.getCell(5).getDateCellValue().toInstant()
                        .atZone(java.time.ZoneId.systemDefault()).toLocalDate());
            } else {
                asegurado.setFechaNacimiento(LocalDate.parse(dateFormat.format(row.getCell(5).getDateCellValue())));
            }

            asegurado.setGenero(row.getCell(6).getStringCellValue());
            asegurado.setDireccion(row.getCell(7).getStringCellValue());
            asegurado.setEmail(row.getCell(8).getStringCellValue());
            asegurado.setTelefono(row.getCell(9).getStringCellValue());
            asegurado.setVigente(true);

            nuevosAsegurados.add(asegurado);
        }

        actualizarVigencia(nuevosAsegurados);
        registrarUsuarios(nuevosAsegurados);

        workbook.close();
    }

    private void actualizarVigencia(List<Asegurado> nuevosAsegurados) {
        List<Asegurado> existentes = aseguradoRepository.findAll();

        for (Asegurado existente : existentes) {
            boolean sigueEnExcel = nuevosAsegurados.stream()
                    .anyMatch(nuevo -> nuevo.getCi().equals(existente.getCi()) && nuevo.getComplemento().equals(existente.getComplemento()));
            if (!sigueEnExcel) {
                existente.setVigente(false);
                aseguradoRepository.save(existente);
            }
        }

        aseguradoRepository.saveAll(nuevosAsegurados);
    }

    private void registrarUsuarios(List<Asegurado> nuevosAsegurados) {
        for (Asegurado asegurado : nuevosAsegurados) {
            if (!usuarioRepository.existsByUsername(asegurado.getEmail())) {
                String password = generarContraseñaAleatoria();
                Usuario usuario = new Usuario();
                usuario.setUsername(asegurado.getEmail());
                usuario.setPassword(password); // Encripta esta contraseña antes de guardar
                usuario.setActivo(true);

                usuarioRepository.save(usuario);
                emailService.enviarCredenciales(usuario.getUsername(), password);
            }
        }
    }

    private String generarContraseñaAleatoria() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(10);

        for (int i = 0; i < 10; i++) {
            password.append(caracteres.charAt(random.nextInt(caracteres.length())));
        }
        return password.toString();
    }
}
