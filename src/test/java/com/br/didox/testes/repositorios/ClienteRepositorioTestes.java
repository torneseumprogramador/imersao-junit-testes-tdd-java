package com.br.didox.testes.repositorios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.br.didox.testes.models.Cliente;

@SpringBootTest
public class ClienteRepositorioTestes {
    @Autowired
    private IClienteRepositorio repo;

    @BeforeEach
    private void limparClientes(){
        repo.deleteAll();
    }

    private Cliente usuarioFake() {
        var cliente = new Cliente();
		cliente.setNome("Danilo");
		cliente.setCpf("642.236.630-20");
		cliente.setTelefone("(11) 99999-9999");
        return cliente;
    }

    @Test
	void salvarUsuario() {
        var cliente = usuarioFake();
        repo.save(cliente);

        var quantidade = repo.count();
        assertEquals(1, quantidade);

        var clienteDb = repo.findById(cliente.getId());
        assertNotNull(clienteDb);
    }

    @Test
	void alteraUsuario() {
        var cliente = usuarioFake();
        repo.save(cliente);
        cliente.setNome("Tadeu");
        repo.save(cliente);

        var clienteDb = repo.findByName("Tadeu");
        assertNotNull(clienteDb);
    }

    @Test
	void excluiUsuario() {
        var cliente = usuarioFake();
        repo.save(cliente);

        int id = cliente.getId();
        repo.deleteById(id);

        var clienteDb = repo.findById(id).get();
        assertNull(clienteDb);
    }
}
