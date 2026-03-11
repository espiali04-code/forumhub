package com.alura.forumhub.controller;

import com.alura.forumhub.dto.TopicoRequestDTO;
import com.alura.forumhub.dto.TopicoResponseDTO;
import com.alura.forumhub.model.Topico;
import com.alura.forumhub.repository.TopicoRepository;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    private final TopicoRepository repository;

    public TopicoController(TopicoRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public TopicoResponseDTO crear(@RequestBody @Valid TopicoRequestDTO datos) {

        Topico topico = new Topico();
        topico.setTitulo(datos.getTitulo());
        topico.setMensaje(datos.getMensaje());
        topico.setAutor(datos.getAutor());
        topico.setCurso(datos.getCurso());
        topico.setFechaCreacion(LocalDateTime.now());

        Topico guardado = repository.save(topico);

        return new TopicoResponseDTO(
                guardado.getId(),
                guardado.getTitulo(),
                guardado.getMensaje(),
                guardado.getAutor(),
                guardado.getCurso(),
                guardado.getFechaCreacion()
        );
    }

    @GetMapping
    public List<Topico> listar() {
        return repository.findAll();
    }

    // UPDATE
    @PutMapping("/{id}")
    public Topico actualizar(@PathVariable Long id, @RequestBody @Valid TopicoRequestDTO datos) {

        Topico topico = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Topico no encontrado"));

        topico.setTitulo(datos.getTitulo());
        topico.setMensaje(datos.getMensaje());
        topico.setAutor(datos.getAutor());
        topico.setCurso(datos.getCurso());

        return repository.save(topico);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        repository.deleteById(id);
    }
}