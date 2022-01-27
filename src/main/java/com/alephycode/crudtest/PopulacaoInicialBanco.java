package com.alephycode.crudtest;

import com.alephycode.crudtest.dominio.Pessoa;
import com.alephycode.crudtest.dominio.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Component
@Transactional
public class PopulacaoInicialBanco implements CommandLineRunner {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Override
    public void run(String... args) throws Exception {
        Pessoa p1 = new Pessoa("Jão");
        p1.setDataNascimento(LocalDate.of(1990, 4, 1));
        p1.setEmail("jao@gmail.com");

        Pessoa p2 = new Pessoa("Maria");
        p2.setDataNascimento(LocalDate.of(1980, 8, 2));
        p2.setEmail("maria@gmail.com");

        pessoaRepository.save(p1);
        pessoaRepository.save(p2);

    }
}
