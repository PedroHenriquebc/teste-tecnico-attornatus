package com.api.testetecnico.repositories;

import com.api.testetecnico.models.Endereco;
import com.api.testetecnico.models.TipoEndereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

    List<Endereco> findByPessoa_id(int id);

    Endereco findByPessoa_idAndTipo(int pessoaId, TipoEndereco tipoEndereco);
}
