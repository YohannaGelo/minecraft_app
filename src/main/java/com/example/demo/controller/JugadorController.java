package com.example.demo.controller;

import com.example.demo.model.Inventario;
import com.example.demo.model.Jugador;
import com.example.demo.repository.JugadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class JugadorController {

    @Autowired
    private JugadorRepository jugadorRepository;

    // Mostrar la lista de jugadores
    @GetMapping({ "/jugadores", "/" })
    public String listarJugadores(Model modelo) {
        modelo.addAttribute("jugadores", jugadorRepository.findAll());
        return "jugadores"; // Retorna la vista "jugadores.html"
    }

    // Mostrar el formulario para crear un nuevo jugador
    @GetMapping("/jugadores/crear")
    public String formularioCrear(Model modelo) {
        Jugador jugador = new Jugador();
        modelo.addAttribute("jugador", jugador);
        return "crear"; // Retorna la vista "crear.html"
    }
    
    @PostMapping("/jugadores")
    public String guardarJugador(@ModelAttribute("jugador") Jugador jugador, @RequestParam String items) {
        // Crear un nuevo inventario
        Inventario inventario = new Inventario();
        inventario.setItems(items); // Asignar los items del formulario al inventario

        // Asociar el inventario al jugador
        jugador.setInventario(inventario);

        // Guardar el jugador (y el inventario debido a la relación en cascada)
        jugadorRepository.save(jugador);

        return "redirect:/jugadores"; // Redirige a la lista de jugadores
    }

    // Mostrar el formulario para editar un jugador existente
    @GetMapping("/jugadores/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model modelo) {
        Jugador jugador = jugadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));
        modelo.addAttribute("jugador", jugador);
        return "editar"; // Retorna la vista "editar.html"
    }

    // Actualizar un jugador existente
    @PostMapping("/jugadores/{id}")
    public String actualizarJugador(@PathVariable Long id, @ModelAttribute("jugador") Jugador jugador, @RequestParam String items) {
        // Obtener el jugador existente
        Jugador jugadorExistente = jugadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));

        // Actualizar los campos del jugador
        jugadorExistente.setNombre(jugador.getNombre());
        jugadorExistente.setNivel(jugador.getNivel());

        // Actualizar los items del inventario
        jugadorExistente.getInventario().setItems(items);

        // Guardar el jugador (y el inventario debido a la relación en cascada)
        jugadorRepository.save(jugadorExistente);

        return "redirect:/jugadores"; // Redirige a la lista de jugadores
    }
    

    // Eliminar un jugador
    @GetMapping("/jugadores/{id}")
    public String eliminarJugador(@PathVariable Long id) {
        jugadorRepository.deleteById(id);
        return "redirect:/jugadores"; // Redirige a la lista de jugadores
    }

    // Mostrar detalles de un jugador
    @GetMapping("/jugadores/detalles/{id}")
    public String detallesJugador(@PathVariable Long id, Model modelo) {
        Jugador jugador = jugadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));
        modelo.addAttribute("jugador", jugador);
        return "detalles"; // Retorna la vista "detalles.html"
    }
}