package com.api.testetecnico.controllers;

import com.api.testetecnico.models.Endereco;
import com.api.testetecnico.models.Pessoa;
import com.api.testetecnico.models.TipoEndereco;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class EnderecoControllerTest {

    @Autowired
    private EnderecoController enderecoController;

    @Autowired
    private PessoaController pessoaController;

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
        return endereco;
    }

    @Test
    @DisplayName("O método salvarEndereco deverá requisitar o salvamento de um endereço para o Service e salvá-lo")
    public void salvarEndereco(){
        Pessoa pessoa = criarPessoa();
        Endereco endereco = criarEndereco(pessoa);

        Pessoa pessoaSalva = (Pessoa) this.pessoaController.salvarPessoa(pessoa).getBody();

        Endereco enderecoSalvo = (Endereco) this.enderecoController.salvarEndereco(pessoaSalva.getId(), endereco).getBody();

        assertNotNull(enderecoSalvo);
        assertNotNull(enderecoSalvo.getId());
    }

    @Test
    @DisplayName("O método listarEnderecoPessoa deverá requisitar a lista de endereços de uma pessoa para o Service")
    public void lsitarEnderecoPessoa(){
        Pessoa pessoa = criarPessoa();
        Endereco endereco = criarEndereco(pessoa);

        Pessoa pessoaSalva = (Pessoa) this.pessoaController.salvarPessoa(pessoa).getBody();

        Endereco enderecoSalvo = (Endereco) this.enderecoController.salvarEndereco(pessoaSalva.getId(), endereco).getBody();

        List<Endereco> listaEndereco = (List<Endereco>) this.enderecoController.listarEnderecoPessoa(pessoaSalva.getId()).getBody();

        assertEquals(ArrayList.class, listaEndereco.getClass());
        assertEquals(1,listaEndereco.size());
    }

    @Test
    @DisplayName("O método consultarEnderecoPrincipal deverá requisitar o endereço principal de uma pessoa para o Service")
    public void listarEnderecoPrincipal(){
        Pessoa pessoa = criarPessoa();
        Endereco endereco1 = criarEndereco(pessoa);
        Endereco endereco2 = criarEndereco(pessoa);
        endereco2.setTipo(TipoEndereco.ALTERNATIVO);

        Pessoa pessoaSalva = (Pessoa) this.pessoaController.salvarPessoa(pessoa).getBody();

        Endereco enderecoSalvo1 = (Endereco) this.enderecoController.salvarEndereco(pessoaSalva.getId(), endereco1).getBody();
        this.enderecoController.salvarEndereco(pessoaSalva.getId(), endereco2).getBody();

        Endereco enderecoRetornado = (Endereco) this.enderecoController.consultarEnderecoPrincipal(pessoa.getId()).getBody();

        assertEquals(TipoEndereco.PRINCIPAL, enderecoRetornado.getTipo());
        assertEquals(enderecoSalvo1.getPessoa().getId(), enderecoRetornado.getPessoa().getId());
    }
}
