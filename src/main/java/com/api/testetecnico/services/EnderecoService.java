package com.api.testetecnico.services;

import com.api.testetecnico.models.Endereco;
import com.api.testetecnico.models.TipoEndereco;
import com.api.testetecnico.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Transactional
    public Endereco salvar(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    public List<Endereco> listar(int id) {
        return enderecoRepository.findByPessoa_id(id);
    }

    public Endereco consultar(int id) {
        return enderecoRepository.findByIdAndTipo(id, TipoEndereco.PRINCIPAL);
    }
}
