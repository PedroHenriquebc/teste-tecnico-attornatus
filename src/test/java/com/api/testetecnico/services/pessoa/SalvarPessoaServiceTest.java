package com.api.testetecnico.services.pessoa;

import com.api.testetecnico.models.Pessoa;
import com.api.testetecnico.services.PessoaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class SalvarPessoaServiceTest {

    @Autowired
    private PessoaService pessoaService;

    @Test
    @DisplayName("Uma nova pessoa deverá ser salva no banco de dados")
    public void salvarTest() {

        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Pedro");
        pessoa.setDataNascimento(LocalDate.of(1991, 01, 01));

        Pessoa pessoaSalva = pessoaService.salvar(pessoa);

        assertNotNull(pessoaSalva.getId());
    }
}
