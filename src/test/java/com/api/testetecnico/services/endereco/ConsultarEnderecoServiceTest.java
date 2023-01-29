package com.api.testetecnico.services.endereco;
import com.api.testetecnico.models.Endereco;
import com.api.testetecnico.models.Pessoa;
import com.api.testetecnico.models.TipoEndereco;
import com.api.testetecnico.services.EnderecoService;
import com.api.testetecnico.services.PessoaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ConsultarEnderecoServiceTest {

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private PessoaService pessoaService;

    @Test
    @DisplayName("O endereço principal de uma pessoa deverá ser retornados")
    public void listarEnderecoTest() {

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
        endereco2.setTipo(TipoEndereco.ALTERNATIVO);
        endereco2.setPessoa(pessoa);

        enderecoService.salvar(endereco1);
        enderecoService.salvar(endereco2);


        assertEquals(TipoEndereco.PRINCIPAL, enderecoService.consultar(1).getTipo());
    }
}
