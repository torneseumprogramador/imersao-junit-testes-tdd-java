package com.br.didox.testes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.didox.testes.modelViews.Erro;
import com.br.didox.testes.models.Cliente;
import com.br.didox.testes.repositorios.IClienteRepositorio;
import com.br.didox.testes.servicos.ClienteServico;

@RestController
@RequestMapping("/clientes")
public class ClientesController {

    @Autowired
    private IClienteRepositorio repo;

    @GetMapping("")
    public Iterable<Cliente> index(){
        return repo.findAll();
    }

    @PostMapping("")
    public ResponseEntity<Object> create(@RequestBody Cliente cliente){
        if(!ClienteServico.validarCPF(cliente))
            return ResponseEntity.status(400).body(new Erro("O CPF passado foi invalido"));

        repo.save(cliente);
        return ResponseEntity.status(201).body(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable int id, @RequestBody Cliente cliente){
        if(!repo.existsById(id)) return ResponseEntity.status(404).build();

        if(!ClienteServico.validarCPF(cliente))
            return ResponseEntity.status(400).body(new Erro("O CPF passado foi invalido"));

        var clienteDb = repo.findById(id).get();
        clienteDb.setNome(cliente.getNome());
        clienteDb.setTelefone(cliente.getTelefone());
        clienteDb.setCpf(cliente.getCpf());
        repo.save(clienteDb);

        return ResponseEntity.status(200).body(clienteDb);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Cliente> delete(@PathVariable int id){
        if(!repo.existsById(id)) return ResponseEntity.status(404).build();

        repo.deleteById(id);
        return ResponseEntity.status(204).build();
    }
}
