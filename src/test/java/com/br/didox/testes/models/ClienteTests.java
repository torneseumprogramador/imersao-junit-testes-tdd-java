package com.br.didox.testes.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ClienteTests {

	@Test
	void validarPropriedades() {
		var cliente = new Cliente();
		cliente.setId(1);
		cliente.setNome("Danilo");
		cliente.setCpf("642.236.630-20");
		cliente.setTelefone("(11) 99999-9999");

		assertEquals(1, cliente.getId());
		assertEquals("DANILO", cliente.getNome());
		assertEquals("642.236.630-20", cliente.getCpf());
		assertEquals("(11) 99999-9999", cliente.getTelefone());
	}

}
