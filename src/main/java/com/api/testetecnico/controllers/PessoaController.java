package com.api.testetecnico.controllers;

import com.api.testetecnico.models.Pessoa;
import com.api.testetecnico.services.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @PostMapping
    public ResponseEntity<Object> salvarPessoa(@RequestBody @Valid Pessoa pessoa){
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaService.salvar(pessoa));
    }

    @GetMapping
    public ResponseEntity<List<Pessoa>> listarPessoas(){
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> consultarPessoa(@PathVariable(value = "id") int id){
        Optional<Pessoa> pessoaOptional = pessoaService.consultar(id);
        if (pessoaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encontrada.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(pessoaOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> excluirPessoa(@PathVariable(value = "id") int id){
        Optional<Pessoa> pessoaOptional = pessoaService.consultar(id);
        if (pessoaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encontrada.");
        }
        pessoaService.excluir(pessoaOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Pessoa excluída com sucesso.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> editarPessoa(@PathVariable(value = "id") int id, @RequestBody @Valid Pessoa pessoaAtualizada){
        Optional<Pessoa> pessoaOptional = pessoaService.consultar(id);
        if (pessoaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encontrada.");
        }
        var pessoa = new Pessoa();
        BeanUtils.copyProperties(pessoaAtualizada, pessoa);
        pessoa.setId(pessoaOptional.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.salvar(pessoa));
    }
}
