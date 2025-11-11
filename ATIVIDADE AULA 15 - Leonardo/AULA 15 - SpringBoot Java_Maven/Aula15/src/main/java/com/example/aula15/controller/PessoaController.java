package com.example.aula15.controller;

import com.example.aula15.model.Pessoa;

import com.example.aula15.repository.PessoaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    private final PessoaRepository pessoaRepository;

    public PessoaController(PessoaRepository pessoaRepository) {
        super();
        this.pessoaRepository = pessoaRepository;

    }

    @GetMapping
    public List<Pessoa> listar() {
        return pessoaRepository.findAll();
    }

    @GetMapping("/{id}")
    public Pessoa listar(@PathVariable Long id) {
        if (!pessoaRepository.existsById(id)) {
            return null;
        }


        return pessoaRepository.findById(id).orElse(null);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pessoa adicionar (@RequestBody Pessoa cliente) {

        return pessoaRepository.save(cliente);
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        if (!pessoaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        pessoaRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> atualizarPessoa(@PathVariable Long id, @RequestBody Pessoa pessoaAtualizado) {

        if (!pessoaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        Pessoa pessoaExistente = pessoaRepository.findById(id).orElse(null);

        if (pessoaExistente != null) {

            pessoaExistente.setNome(pessoaAtualizado.getNome());
            pessoaExistente.setSexo(pessoaAtualizado.getSexo());
            pessoaExistente.setIdioma(pessoaAtualizado.getIdioma());

            Pessoa pessoaAtualizadoNoBanco = pessoaRepository.save(pessoaExistente);

            return ResponseEntity.ok(pessoaAtualizadoNoBanco);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
