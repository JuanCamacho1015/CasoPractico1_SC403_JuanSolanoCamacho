package com.mycompany.casopractica1.controller;

import com.mycompany.casopractica1.domain.Sala;
import com.mycompany.casopractica1.service.SalaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/sala")
public class SalaController {

    private final SalaService salaService;

    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        model.addAttribute("salas", salaService.listar());
        return "sala/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("sala", new Sala());
        model.addAttribute("modo", "crear");
        return "sala/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("sala") Sala sala,
                          BindingResult br,
                          RedirectAttributes ra,
                          Model model) {
        if (br.hasErrors()) {
            model.addAttribute("modo", "crear");
            return "sala/formulario";
        }
        try {
            salaService.guardar(sala);
            ra.addFlashAttribute("success", "Sala registrada correctamente.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/sala/listado";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes ra) {
        var opt = salaService.buscarPorId(id);
        if (opt.isEmpty()) {
            ra.addFlashAttribute("error", "Sala no encontrada.");
            return "redirect:/sala/listado";
        }
        model.addAttribute("sala", opt.get());
        model.addAttribute("modo", "editar");
        return "sala/formulario";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Long id,
                             @Valid @ModelAttribute("sala") Sala sala,
                             BindingResult br,
                             RedirectAttributes ra,
                             Model model) {
        if (br.hasErrors()) {
            model.addAttribute("modo", "editar");
            return "sala/formulario";
        }
        try {
            salaService.actualizar(id, sala);
            ra.addFlashAttribute("success", "Sala actualizada correctamente.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/sala/listado";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        try {
            salaService.eliminar(id);
            ra.addFlashAttribute("success", "Sala eliminada.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/sala/listado";
    }
}