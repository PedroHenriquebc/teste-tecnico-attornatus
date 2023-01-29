package com.api.testetecnico.services.pessoa;

import com.api.testetecnico.models.Pessoa;
import com.api.testetecnico.services.PessoaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ConsultarPessoaServiceTest {

    @Autowired
    private PessoaService pessoaService;

    @Test
    @DisplayName("Uma Optional vazio deverá ser retornado ao tentar consultar um Id inexistente")
    public void consultarIdInexistenteTest() {

        assertEquals(Optional.empty(), (pessoaService.consultar(100)));
    }

    @Test
    @DisplayName("Uma pessoa deverá ser retornada")
    public void consultarTest() {

        Pessoa pessoa1 = new Pessoa();
        pessoa1.setNome("Pedro");
        pessoa1.setDataNascimento(LocalDate.of(1991, 01, 01));

        pessoaService.salvar(pessoa1);

        Pessoa pessoa1Salva = pessoaService.consultar(1).get();

        assertEquals(1, pessoa1Salva.getId());
        assertEquals("Pedro", pessoa1Salva.getNome());
        assertEquals(LocalDate.of(1991,01,01), pessoa1Salva.getDataNascimento());
    }
}
