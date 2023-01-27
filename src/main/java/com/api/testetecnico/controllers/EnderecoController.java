package com.api.testetecnico.controllers;

import com.api.testetecnico.models.Endereco;
import com.api.testetecnico.models.Pessoa;
import com.api.testetecnico.services.EnderecoService;
import com.api.testetecnico.services.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private PessoaService pessoaService;

    @PostMapping("/{id}")
    public ResponseEntity<Object> salvarEndereco(@PathVariable(value = "id") int id, @RequestBody @Valid Endereco endereco){
        Optional<Pessoa> pessoaOptional = pessoaService.consultar(id);
        if (pessoaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encontrada.");
        }
        endereco.setPessoa(pessoaOptional.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoService.salvar(endereco));
    }

    @GetMapping("/pessoa/{id}")
    public ResponseEntity<Object> listarEnderecoPessoa(@PathVariable(value = "id")int id) {
        Optional<Pessoa> pessoaOptional = pessoaService.consultar(id);
        if (pessoaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encontrada.");
        }
        return  ResponseEntity.status(HttpStatus.OK).body(enderecoService.listar(id));
    }

    @GetMapping("/principal/{id}")
    public ResponseEntity<Object> consultarPrincipal(@PathVariable(value = "id")int id) {
        Optional<Pessoa> pessoaOptional = pessoaService.consultar(id);
        if (pessoaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encontrada.");
        }
        return  ResponseEntity.status(HttpStatus.OK).body(enderecoService.consultar(id));
    }
}
