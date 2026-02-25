package com.mycompany.casopractica1.controller;

import com.mycompany.casopractica1.domain.Reunion;
import com.mycompany.casopractica1.service.ReunionService;
import com.mycompany.casopractica1.service.SalaService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//Controlador Reunion, maneja las reuniones programadas

@Controller
@RequestMapping("/reunion")
public class ReunionController {

    private final ReunionService reunionService;
    private final SalaService salaService;

    public ReunionController(ReunionService reunionService, SalaService salaService) {
        this.reunionService = reunionService;
        this.salaService = salaService;
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        model.addAttribute("reuniones", reunionService.listar());
        model.addAttribute("salas", salaService.listar());
        return "reunion/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        List<?> salas = salaService.listar();

        model.addAttribute("reunion", new Reunion());
        model.addAttribute("salas", salas);
        model.addAttribute("modo", "crear");

        // Ya NO redirige a /sala/nuevo. Solo muestra aviso si no hay salas.
        if (salas.isEmpty()) {
            model.addAttribute("error", "Debe registrar al menos una sala antes de crear reuniones.");
        }

        return "reunion/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("reunion") Reunion reunion,
                          BindingResult br,
                          RedirectAttributes ra,
                          Model model) {
        if (br.hasErrors()) {
            model.addAttribute("salas", salaService.listar());
            model.addAttribute("modo", "crear");
            return "reunion/formulario";
        }

        try {
            reunionService.guardar(reunion);
            ra.addFlashAttribute("success", "Reunión registrada correctamente.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/reunion/listado";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes ra) {
        var opt = reunionService.buscarPorId(id);
        if (opt.isEmpty()) {
            ra.addFlashAttribute("error", "Reunión no encontrada.");
            return "redirect:/reunion/listado";
        }

        model.addAttribute("reunion", opt.get());
        model.addAttribute("salas", salaService.listar());
        model.addAttribute("modo", "editar");
        return "reunion/formulario";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Long id,
                             @Valid @ModelAttribute("reunion") Reunion reunion,
                             BindingResult br,
                             RedirectAttributes ra,
                             Model model) {
        if (br.hasErrors()) {
            model.addAttribute("salas", salaService.listar());
            model.addAttribute("modo", "editar");
            return "reunion/formulario";
        }

        try {
            reunionService.actualizar(id, reunion);
            ra.addFlashAttribute("success", "Reunión actualizada correctamente.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/reunion/listado";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        try {
            reunionService.eliminar(id);
            ra.addFlashAttribute("success", "Reunión eliminada.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/reunion/listado";
    }
}