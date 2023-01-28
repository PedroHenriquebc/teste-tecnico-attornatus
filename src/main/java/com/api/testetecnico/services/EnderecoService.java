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
        enderecoRepository.save(endereco);
        int id = enderecoRepository.save(endereco).getPessoa().getId();
        int contadorPrincipal = 0;
        List<Endereco> listaEnderecos = enderecoRepository.findByPessoa_id(id);
        for (Endereco enderecoAuxiliar : listaEnderecos) {
            if (enderecoAuxiliar.getTipo() == TipoEndereco.PRINCIPAL) {
                contadorPrincipal += 1;
            }
        }
        if (contadorPrincipal > 1) {
            for (Endereco enderecoAuxiliar : listaEnderecos) {
                if (enderecoAuxiliar.getTipo() == TipoEndereco.PRINCIPAL) {
                    enderecoAuxiliar.setTipo(TipoEndereco.ALTERNATIVO);
                    break;
                }
            }
        }
        return enderecoRepository.save(endereco);
    }

    public List<Endereco> listar(int id) {
        return enderecoRepository.findByPessoa_id(id);
    }

    public Endereco consultar(int id) {
        return enderecoRepository.findByPessoa_idAndTipo(id, TipoEndereco.PRINCIPAL);
    }
}
