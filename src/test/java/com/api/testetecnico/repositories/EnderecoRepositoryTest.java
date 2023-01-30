package com.api.testetecnico.repositories;

import com.api.testetecnico.models.Endereco;
import com.api.testetecnico.models.Pessoa;
import com.api.testetecnico.models.TipoEndereco;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class EnderecoRepositoryTest {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    private Pessoa criarPessoa() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Peter Parker");
        pessoa.setDataNascimento(LocalDate.of(1993, 5, 12));
        return pessoa;
    }

    private Endereco criarEndereco(Pessoa pessoa) {
        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua Abc");
        endereco.setCep("12345678");
        endereco.setNumero("125");
        endereco.setCidade("Cidadela");
        endereco.setTipo(TipoEndereco.PRINCIPAL);
        endereco.setPessoa(pessoa);
        return endereco;
    }

    @Test
    @DisplayName("O método save deverá persistir um endereço no banco de dados")
    public void salvarEndereco(){
        Pessoa pessoa = criarPessoa();
        Endereco endereco = criarEndereco(pessoa);

        this.pessoaRepository.save(pessoa);
        Endereco enderecoSalvo = this.enderecoRepository.save(endereco);

        assertNotNull(enderecoSalvo.getId());
    }

    @Test
    @DisplayName("O método findByPessoa_id deverá retornar a lista de endereços de uma pessoa do banco de dados")
    public void ListarEndereco(){
        Pessoa pessoa = criarPessoa();
        Endereco endereco1 = criarEndereco(pessoa);
        Endereco endereco2 = criarEndereco(pessoa);

        this.pessoaRepository.save(pessoa);
        Endereco enderecoSalvo1 = this.enderecoRepository.save(endereco1);
        Endereco enderecoSalvo2 = this.enderecoRepository.save(endereco2);

        List<Endereco> listaEndereco = this.enderecoRepository.findByPessoa_id(pessoa.getId());

        assertEquals(ArrayList.class, listaEndereco.getClass());
        assertEquals(2, listaEndereco.size());
        assertEquals(enderecoSalvo1.getPessoa().getId(), listaEndereco.get(0).getPessoa().getId());
        assertEquals(enderecoSalvo2.getPessoa().getId(), listaEndereco.get(1).getPessoa().getId());
    }

    @Test
    @DisplayName("O método findByPessoa_idAndTipo deverá retornar o endereço principal de uma pessoa do banco de dados")
    public void consultarEnderecoPrincipal(){
        Pessoa pessoa = criarPessoa();
        Endereco endereco1 = criarEndereco(pessoa);
        Endereco endereco2 = criarEndereco(pessoa);
        endereco2.setTipo(TipoEndereco.ALTERNATIVO);

        this.pessoaRepository.save(pessoa);
        this.enderecoRepository.save(endereco2);
        Endereco enderecoSalvo1 = this.enderecoRepository.save(endereco1);

        Endereco enderecoRetornado = this.enderecoRepository.findByPessoa_idAndTipo(pessoa.getId(), TipoEndereco.PRINCIPAL);

        assertEquals(TipoEndereco.PRINCIPAL, enderecoRetornado.getTipo());
        assertEquals(enderecoSalvo1.getPessoa().getId(), enderecoRetornado.getPessoa().getId());
    }
}
