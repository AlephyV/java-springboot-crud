package com.alephycode.crudtest.controller;

import com.alephycode.crudtest.Dto.AutoCompleteDTO;
import com.alephycode.crudtest.dominio.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Controller
public class PessoaController {

    private PessoaRepository pessoaRepository;
    private CidadeRepository cidadeRepository;
    private DepartamentoRepository departamentoRepository;

    private List<Departamento> departamentosSugeridos = new ArrayList<>();

    public PessoaController(PessoaRepository pessoaRepository, CidadeRepository cidadeRepository, DepartamentoRepository departamentoRepository) {
        this.pessoaRepository = pessoaRepository;
        this.cidadeRepository = cidadeRepository;
        this.departamentoRepository = departamentoRepository;
    }

    @GetMapping("/pessoas")
    public String pessoas(Model model) {
        model.addAttribute("listaPessoas", pessoaRepository.findAll());
        return "/pessoas/index";
    }

    @GetMapping("/pessoas/nova")
    public String novaPessoa(Model model) {
        model.addAttribute("pessoa", new Pessoa(""));
        model.addAttribute("cidades", cidadeRepository.findAll());
        return "pessoas/form";
    }

    @PostMapping("pessoas/salvar")
    public String salverPessoa(@Valid @ModelAttribute("pessoa") Pessoa pessoa, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("cidades", cidadeRepository.findAll());
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
        model.addAttribute("cidades", cidadeRepository.findAll());

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

    @RequestMapping("pessoas/departamentosNomeAutoComplete")
    @ResponseBody
    public List<AutoCompleteDTO> departamentosNomeAutoComplete(@RequestParam("term") String term) {
        List<AutoCompleteDTO> sugestoes = new ArrayList<>();
        try {
            if(term.length() >= 3) {
                departamentosSugeridos = departamentoRepository.searchByNome(term);
            }

            for(Departamento d : departamentosSugeridos) {
                if(d.getNome().toLowerCase().contains(term.toLowerCase())) {
                    AutoCompleteDTO dto = new AutoCompleteDTO(d.getNome(), Long.toString(d.getId()));
                    sugestoes.add(dto);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return sugestoes;
    }


}
