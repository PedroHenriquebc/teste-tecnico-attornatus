package com.api.testetecnico.controllers;

import com.api.testetecnico.models.Pessoa;
import com.api.testetecnico.services.PessoaService;
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
public class PessoaControllerTest {

    @Autowired
    private PessoaController pessoaController;

    @Autowired
    private PessoaService pessoaService;

    private Pessoa criarPessoa() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Peter Parker");
        pessoa.setDataNascimento(LocalDate.of(1993, 5, 12));
        return pessoa;
    }

    @Test
    @DisplayName("O método salvarPessoa deverá requisitar o salvamento de uma Pessoa para o Service e salvá-la")
    public void salvarPessoa(){
        Pessoa pessoa = criarPessoa();
        Pessoa pessoaSalva = (Pessoa) this.pessoaController.salvarPessoa(pessoa).getBody();

        assertNotNull(pessoaSalva);
        assertNotNull(pessoaSalva.getId());
    }

    @Test
    @DisplayName("O método listarPessoas deverá requisitar a lista de todas as pessoas do Service")
    public void listarPessoas(){
        Pessoa pessoa = criarPessoa();
        this.pessoaController.salvarPessoa(pessoa);

        List<Pessoa> listaPessoa = this.pessoaController.listarPessoas().getBody();

        assertEquals(ArrayList.class, listaPessoa.getClass());
        assertEquals(1, listaPessoa.size());
        assertEquals(pessoa.getId(), listaPessoa.get(0).getId());
    }

    @Test
    @DisplayName("O método consultarPessoa deverá requisitar uma pessoa pelo Id para o Service")
    public void consultarPessoa(){
        Pessoa pessoa = criarPessoa();
        Pessoa pessoaSalva = (Pessoa) this.pessoaController.salvarPessoa(pessoa).getBody();

        Pessoa pessoaEncontrada = (Pessoa) this.pessoaController.consultarPessoa(pessoaSalva.getId()).getBody();

        assertEquals(pessoaSalva.getId(), pessoaEncontrada.getId());
    }

    @Test
    @DisplayName("O método excluirPessoa deverá requisitar a exclusão de uma pessoa pelo Id para o Service")
    public void excluirPessoa(){
        Pessoa pessoa = criarPessoa();
        Pessoa pessoaSalva = (Pessoa) this.pessoaController.salvarPessoa(pessoa).getBody();

        this.pessoaController.excluirPessoa(pessoaSalva.getId());

        assertEquals("Pessoa não encontrada.", this.pessoaController.consultarPessoa(pessoaSalva.getId()).getBody());
    }

    @Test
    @DisplayName("O método editarPessoa deverá requisitar a atualização de uma pessoa pelo Id para o Service")
    public void editarPessoa(){
        Pessoa pessoa = criarPessoa();
        Pessoa pessoaSalva = (Pessoa) this.pessoaController.salvarPessoa(pessoa).getBody();

        Pessoa pessoaAtualizada = criarPessoa();
        pessoaAtualizada.setNome("Barry Alen");

        Pessoa pessoaRetornada = (Pessoa) this.pessoaController.editarPessoa(pessoaSalva.getId(), pessoaAtualizada).getBody();

        assertEquals(pessoaAtualizada.getNome(), pessoaRetornada.getNome());
        assertEquals(pessoaSalva.getId(), pessoaRetornada.getId());
    }
}
