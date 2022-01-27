package com.alephycode.crudtest.controller;

import com.alephycode.crudtest.dominio.Pessoa;
import com.alephycode.crudtest.dominio.PessoaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class PessoaController {

    private PessoaRepository pessoaRepository;

    public PessoaController(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @GetMapping("/pessoas")
    public String pessoas(Model model) {
        model.addAttribute("listaPessoas", pessoaRepository.findAll());
        return "/pessoas/index";
    }

    @GetMapping("/pessoas/nova")
    public String novaPessoa(@ModelAttribute("pessoa") Pessoa pessoa) {
        return "pessoas/form";
    }

    @PostMapping("pessoas/salvar")
    public String salverPessoa(@Valid @ModelAttribute("pessoa") Pessoa pessoa, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "/pessoas/form";
        }
        pessoaRepository.save(pessoa);
        return "redirect:/pessoas";
    }

    @GetMapping("/pessoas/{id}")
    public String alterarPessoa(@PathVariable("id") long id, Model model) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);
        if(pessoa.isEmpty()) {
            throw new IllegalArgumentException("Pessoa inválida!");
        }

        model.addAttribute("pessoa", pessoa.get());

        return "pessoas/form";
    }

    @GetMapping("/pessoas/excluir/{id}")
    public String excluirPessoa(@PathVariable("id") long id, Model model) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);
        if(pessoa.isEmpty()) {
            throw new IllegalArgumentException("Pessoa inválida!");
        }

        pessoaRepository.delete(pessoa.get());

        return "redirect:/pessoas";
    }

}
