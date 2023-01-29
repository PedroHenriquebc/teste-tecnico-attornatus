package com.api.testetecnico.services.pessoa;

import com.api.testetecnico.models.Pessoa;
import com.api.testetecnico.services.PessoaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ListarPessoaServiceTest {

    @Autowired
    private PessoaService pessoaService;

    @Test
    @DisplayName("Uma lista de pessoas deverá ser retornada do banco de dados")
    public void listarTest() {

        Pessoa pessoa1 = new Pessoa();
        pessoa1.setNome("Pedro");
        pessoa1.setDataNascimento(LocalDate.of(1991, 01, 01));

        Pessoa pessoa2 = new Pessoa();
        pessoa2.setNome("Paula");
        pessoa2.setDataNascimento(LocalDate.of(1980, 02, 02));

        pessoaService.salvar(pessoa1);
        pessoaService.salvar(pessoa2);

        List<Pessoa> listaPessoas = pessoaService.listar();
        assertNotNull(listaPessoas);
        assertEquals(2, listaPessoas.size());
    }
}
