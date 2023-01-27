package com.api.testetecnico.services;

import com.api.testetecnico.models.Pessoa;
import com.api.testetecnico.repositories.PessoaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Transactional
    public Pessoa salvar(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public List<Pessoa> listar() {
        return pessoaRepository.findAll();
    }

    public Optional<Pessoa> consultar(int id) {
        return pessoaRepository.findById(id);
    }

    @Transactional
    public void excluir(Pessoa pessoa) {
        pessoaRepository.delete(pessoa);
    }
}
