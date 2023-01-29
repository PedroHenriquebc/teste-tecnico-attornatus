package com.api.testetecnico.services.pessoa;
import com.api.testetecnico.models.Pessoa;
import com.api.testetecnico.repositories.PessoaRepository;
import com.api.testetecnico.services.PessoaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ExcluirPessoaServiceTest {

    @Autowired
    private PessoaService pessoaService;

    @Test
    @DisplayName("Uma Optional vazio deverá ser retornado ao tentar excluir um Id inexistente")
    public void consultarIdInexistenteTest() {

        assertEquals(Optional.empty(), (pessoaService.consultar(100)));
    }

    @Test
    @DisplayName("Uma exclusão de uma pessoa deverá ser realizada")
    public void excluirTest() {

        Pessoa pessoa1 = new Pessoa();
        pessoa1.setNome("Pedro");
        pessoa1.setDataNascimento(LocalDate.of(1991, 01, 01));

        pessoaService.salvar(pessoa1);
        pessoaService.excluir(pessoa1);

        assertEquals(Optional.empty(), (pessoaService.consultar(1)));
    }
}
