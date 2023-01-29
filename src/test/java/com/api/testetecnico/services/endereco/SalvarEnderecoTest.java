package com.api.testetecnico.services.endereco;

import com.api.testetecnico.models.Endereco;
import com.api.testetecnico.models.Pessoa;
import com.api.testetecnico.models.TipoEndereco;
import com.api.testetecnico.repositories.EnderecoRepository;
import com.api.testetecnico.services.EnderecoService;
import com.api.testetecnico.services.PessoaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class SalvarEnderecoTest {

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Test
    @DisplayName("Uma novo endereco deverá ser salvo no banco de dados")
    public void salvarEnderecoTest() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Pedro");
        pessoa.setDataNascimento(LocalDate.of(1991, 01, 01));

        pessoaService.salvar(pessoa);

        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua Abc");
        endereco.setCep("12345678");
        endereco.setNumero("125");
        endereco.setCidade("Cidadela");
        endereco.setTipo(TipoEndereco.PRINCIPAL);
        endereco.setPessoa(pessoa);

        Endereco enderecoSalvo = enderecoService.salvar(endereco);
        assertNotNull(enderecoSalvo.getId());
    }

    @Test
    @DisplayName("Uma novo endereco principal deverá ser salvo e o endereço principal anterior se tornar alternativo")
    public void salvarNovoEnderecoPrincipalTest() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Pedro");
        pessoa.setDataNascimento(LocalDate.of(1991, 01, 01));
        pessoaService.salvar(pessoa);

        Endereco endereco1 = new Endereco();
        endereco1.setLogradouro("Rua Abc");
        endereco1.setCep("12345678");
        endereco1.setNumero("125");
        endereco1.setCidade("Cidadela");
        endereco1.setTipo(TipoEndereco.PRINCIPAL);
        endereco1.setPessoa(pessoa);

        Endereco endereco2 = new Endereco();
        endereco2.setLogradouro("Rua Cba");
        endereco2.setCep("87654321");
        endereco2.setNumero("125");
        endereco2.setCidade("Cidadela");
        endereco2.setTipo(TipoEndereco.PRINCIPAL);
        endereco2.setPessoa(pessoa);

        Endereco enderecoSalvo1 = enderecoService.salvar(endereco1);
        Endereco enderecoSalvo2 = enderecoService.salvar(endereco2);

        assertEquals(enderecoSalvo1.getId(), enderecoRepository.findByPessoa_idAndTipo(1, TipoEndereco.ALTERNATIVO).getId());
        assertEquals(enderecoSalvo2.getId(), enderecoRepository.findByPessoa_idAndTipo(1, TipoEndereco.PRINCIPAL).getId());
    }
}
