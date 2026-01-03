package com.itsqmet.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itsqmet.model.Gastos;
import com.itsqmet.model.Usuario;
import com.itsqmet.repository.UserRepository;
import com.itsqmet.service.GastosService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/gastos")
public class GastosController {

    @Autowired
    private GastosService gastosService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/nuevoGasto")
    public String formGasto(Model model, Authentication authentication) {
        // 1. Obtener el usuario autenticado
        String username = authentication.getName();
        Usuario usuario = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        model.addAttribute("nuevoGasto", new Gastos());
        // 2. Filtrar la lista por el ID del usuario
        model.addAttribute("listaDeGastos", gastosService.mostrarGastoPorUsuario(usuario.getId()));
        return "pages/gastosForm";
    }

   @PostMapping("/registrarGasto")
   public String guardar(@ModelAttribute("nuevoGasto") Gastos gastos, Model model, Authentication authentication) {
       String username = authentication.getName();
       Usuario usuario = userRepository.findByUsername(username)
               .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
       try {
           // Asignar el usuario al objeto gastos antes de guardar
           gastos.setUsuario(usuario);
           gastosService.guardarGasto(gastos);
           return "redirect:/gastos/nuevoGasto";
       } catch (RuntimeException e) {
           model.addAttribute("error", e.getMessage());
           model.addAttribute("nuevoGasto", gastos);
           model.addAttribute("listaDeGastos", gastosService.mostrarGastoPorUsuario(usuario.getId()));
           return "pages/gastosForm";
       }
   }

    //Actualizar
    @GetMapping("/editarGasto/{id}")
    public String actualizarGastos(@PathVariable Long id, Model model, Authentication authentication) {
        String username = authentication.getName();
        Usuario usuario = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Optional<Gastos> gastos = gastosService.buscarGastosById(id);
        model.addAttribute("nuevoGasto", gastos.orElse(new Gastos()));
        model.addAttribute("listaDeGastos", gastosService.mostrarGastoPorUsuario(usuario.getId()));
        return "pages/gastosForm";
    }

    //Eliminar libro
    @DeleteMapping("/eliminarGasto/{id}")
    public String eliminarGastos(@PathVariable Long id) {
        gastosService.eliminarGasto(id);
        return "redirect:/gastos/nuevoGasto";
    }

    @GetMapping("/pdf")
    public  void generarPdf(HttpServletResponse response, Authentication authentication) throws  Exception{
        //Obtener el usuario autenticado para filtrar los datos
        String username = authentication.getName();
        Usuario usuario = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        response.setContentType("/gastos/pdf");
        response.setHeader("Content-Disposition", "inline; filename=gastos.pdf");
        Document documento = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(documento, response.getOutputStream());
        documento.open();
        //Titulo
        documento.add(new Paragraph("Proyeccion de Gastos Personales Ingresadas al Sistema"));
        documento.add(new Paragraph(" "));
        //Crear una tabla que contenga los atributis de libro
        PdfPTable gasto = new PdfPTable(7);
        gasto.setWidthPercentage(100);
        //cabecera de la tabla
        gasto.addCell("Periodo");
        gasto.addCell("Vivienda");
        gasto.addCell("Educacion");
        gasto.addCell("Salud");
        gasto.addCell("Vestimenta");
        gasto.addCell("Alimentacion");
        gasto.addCell("Turismo");
        // Traer los datos de la db por medio del servicio
        for (Gastos gastos: gastosService.mostrarGastoPorUsuario(usuario.getId())){
            gasto.addCell(String.valueOf(gastos.getPeriodo()));
            gasto.addCell(String.valueOf(gastos.getVivienda()));
            gasto.addCell(String.valueOf(gastos.getEducacion()));
            gasto.addCell(String.valueOf(gastos.getSalud()));
            gasto.addCell(String.valueOf(gastos.getVestimenta()));
            gasto.addCell(String.valueOf(gastos.getAlimentacion()));
            gasto.addCell(String.valueOf(gastos.getTurismo()));
        }
        documento.add(gasto);
        documento.close();

    }

}
