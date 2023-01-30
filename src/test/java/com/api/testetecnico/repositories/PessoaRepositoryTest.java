package com.api.testetecnico.repositories;

import com.api.testetecnico.models.Pessoa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class PessoaRepositoryTest {

    @Autowired
    private PessoaRepository pessoaRepository;

    private Pessoa criarPessoa() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Peter Parker");
        pessoa.setDataNascimento(LocalDate.of(1993, 5, 12));
        return pessoa;
    }

    @Test
    @DisplayName("O método save deverá persistir uma pessoa no banco de dados")
    public void salvarPessoa(){
        Pessoa pessoa = criarPessoa();

        Pessoa pessoaSalva = this.pessoaRepository.save(pessoa);

        assertNotNull(pessoaSalva.getId());
    }

    @Test
    @DisplayName("O método save deverá atualizar uma pessoa no banco de dados")
    public void editarPessoa(){
        Pessoa pessoa = criarPessoa();

        Pessoa pessoaSalva = this.pessoaRepository.save(pessoa);

        pessoaSalva.setNome("Bruce Wayne");

        Pessoa pessoaAtualizada = this.pessoaRepository.save(pessoaSalva);

        assertNotNull(pessoaAtualizada);
        assertEquals(pessoaSalva.getNome(), pessoaAtualizada.getNome());
    }

    @Test
    @DisplayName("O método delete deverá remover uma pessoa do banco de dados")
    public void excluirPessoa(){
        Pessoa pessoa = criarPessoa();

        Pessoa pessoaSalva = this.pessoaRepository.save(pessoa);

        this.pessoaRepository.delete(pessoaSalva);

        assertEquals(Optional.empty(), this.pessoaRepository.findById(pessoaSalva.getId()));
    }

    @Test
    @DisplayName("O método findById deverá retornar uma pessoa do banco de dados")
    public void consultarPessoa(){
        Pessoa pessoa = criarPessoa();

        Pessoa pessoaSalva = this.pessoaRepository.save(pessoa);

        Pessoa pessoaEncontrada = this.pessoaRepository.findById(pessoaSalva.getId()).get();

        assertEquals(pessoaSalva.getId(), (pessoaEncontrada.getId()));
    }

    @Test
    @DisplayName("O método findAll deverá retornar a lista de todas as pessoas no banco de dados")
    public void ListarPessoas(){
        Pessoa pessoa1 = criarPessoa();
        Pessoa pessoa2 = criarPessoa();

        Pessoa pessoaSalva1 = this.pessoaRepository.save(pessoa1);
        Pessoa pessoaSalva2 = this.pessoaRepository.save(pessoa2);

        List<Pessoa> listaPessoas = this.pessoaRepository.findAll();

        assertEquals(ArrayList.class, listaPessoas.getClass());
        assertEquals(2, listaPessoas.size());
        assertEquals(pessoaSalva1.getId(), listaPessoas.get(0).getId());
        assertEquals(pessoaSalva2.getId(), listaPessoas.get(1).getId());
    }
}
