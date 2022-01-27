package com.alephycode.crudtest;

import com.alephycode.crudtest.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

@Component
@Transactional
public class PopulacaoInicialBanco implements CommandLineRunner {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Override
    public void run(String... args) throws Exception {
        Cidade c1 = new Cidade("Paranavai", "PR");
        Cidade c2 = new Cidade("Correntes", "PE");

        cidadeRepository.save(c1);
        cidadeRepository.save(c2);
        cidadeRepository.flush();

        Departamento d1 = new Departamento("Tecnologia da informação", "TI");
        Departamento d2 = new Departamento("Tecnologia de Alimentos", "TA");
        Departamento d3 = new Departamento("Recursos Humanos", "RH");

        departamentoRepository.save(d1);
        departamentoRepository.save(d2);
        departamentoRepository.save(d3);
        departamentoRepository.flush();

        Pessoa p1 = new Pessoa("Jão");
        p1.setDataNascimento(LocalDate.of(1990, 4, 1));
        p1.setEmail("jao@gmail.com");
        p1.setCidade(c1);
        p1.setDepartamento(d1);

        Pessoa p2 = new Pessoa("Maria");
        p2.setDataNascimento(LocalDate.of(1980, 8, 2));
        p2.setEmail("maria@gmail.com");
        p2.setCidade(c2);
        p2.setDepartamento(d2);

        pessoaRepository.save(p1);
        pessoaRepository.save(p2);

        Endereco e1 = new Endereco("Rua da hora", "12");
        e1.setPessoa(p1);
        Endereco e2 = new Endereco("Rua Recife", "111");
        e2.setPessoa(p1);
        Endereco e3 = new Endereco("Rua paraiso", "55");
        e3.setPessoa(p2);

        enderecoRepository.save(e1);
        enderecoRepository.save(e2);
        enderecoRepository.save(e3);

    }
}
