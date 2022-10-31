package com.br.didox.testes.mockMVC;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.br.didox.testes.modelViews.Erro;
import com.br.didox.testes.models.Cliente;
import com.br.didox.testes.repositorios.IClienteRepositorio;
import com.google.gson.Gson;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientesMockMVCTests {

    @Autowired
    private IClienteRepositorio repo;

    @Autowired
    private MockMvc mockMVC;

    @Test
    public void buscaClientesControllerMock() throws Exception{
        mockMVC.perform(MockMvcRequestBuilders.get("/clientes"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void cadastrarClientesControllerMock() throws Exception{
        var cliente = getCliente();

        String jsonFile = new Gson().toJson(cliente);
		
		mockMVC.perform(MockMvcRequestBuilders.post("/clientes")
            .content(jsonFile)
            .contentType(MediaType.APPLICATION_JSON))
		    .andExpect(status().isCreated());
	}

    @Test
    public void alterarClientesControllerMock() throws Exception{
        var cliente = getCliente();
        repo.save(cliente);

        cliente.setNome("Danilo Aparecido");
        String jsonFile = new Gson().toJson(cliente);
		
		mockMVC.perform(MockMvcRequestBuilders.put("/clientes/" + cliente.getId())
            .content(jsonFile)
            .contentType(MediaType.APPLICATION_JSON))
		    .andExpect(status().isOk());

        var clienteDb = repo.findById(cliente.getId()).get();
        assertEquals("DANILO APARECIDO", clienteDb.getNome());
	}

    @Test
    public void alterarComRetornoClientesControllerMock() throws Exception{
        var cliente = getCliente();
        repo.save(cliente);

        cliente.setNome("Danilo Aparecido");
        String jsonFile = new Gson().toJson(cliente);
		
		MvcResult result = mockMVC.perform(MockMvcRequestBuilders.put("/clientes/" + cliente.getId())
            .content(jsonFile)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

		String jsonString = result.getResponse().getContentAsString();
		Cliente clienteRest = new Gson().fromJson(jsonString, Cliente.class);
		assertEquals("DANILO APARECIDO", clienteRest.getNome());
	}

    @Test
    public void alterar400ComRetornoClientesControllerMock() throws Exception{
		mockMVC.perform(MockMvcRequestBuilders.put("/clientes/1000")
            .content("")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
	}

    @Test
    public void alterar404ComRetornoClientesControllerMock() throws Exception{
        String jsonFile = new Gson().toJson(getCliente());
        
        mockMVC.perform(MockMvcRequestBuilders.put("/clientes/1000")
            .content(jsonFile)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
	}

    @Test
    public void deletarClientesControllerMock() throws Exception{
        var cliente = getCliente();
        repo.save(cliente);

		mockMVC.perform(MockMvcRequestBuilders.delete("/clientes/" + cliente.getId())
			.contentType(MediaType.APPLICATION_JSON))
		    .andExpect(status().isNoContent());

        var clienteDb = repo.findById(cliente.getId());
        assertEquals(true, clienteDb.isEmpty());
	}

    @Test
    public void deletar404ClientesControllerMock() throws Exception{
		mockMVC.perform(MockMvcRequestBuilders.delete("/clientes/1000")
			.contentType(MediaType.APPLICATION_JSON))
		    .andExpect(status().isNotFound());
	}

    @Test
    public void cadastrarCpfInvalidoClientesControllerMock() throws Exception{
        var cliente = getClienteCpfInvalido();

        String jsonFile = new Gson().toJson(cliente);
		
        MvcResult result = mockMVC.perform(MockMvcRequestBuilders.post("/clientes")
            .content(jsonFile)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andReturn();

		String jsonString = result.getResponse().getContentAsString();
		Erro erro = new Gson().fromJson(jsonString, Erro.class);
		assertEquals("O CPF passado foi invalido", erro.getMensagem());
	}

    @Test
    public void alterarCpfInvalidoClientesControllerMock() throws Exception{
        var cliente = getClienteCpfInvalido();
        repo.save(cliente);

        cliente.setNome("Danilo Aparecido");
        String jsonFile = new Gson().toJson(cliente);
		
        MvcResult result = mockMVC.perform(MockMvcRequestBuilders.put("/clientes/" + cliente.getId())
        .content(jsonFile)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andReturn();

        String jsonString = result.getResponse().getContentAsString();
        Erro erro = new Gson().fromJson(jsonString, Erro.class);
        assertEquals("O CPF passado foi invalido", erro.getMensagem());
	}

    private Cliente getClienteCpfInvalido() {
        var cliente = new Cliente();
        cliente.setCpf("642.236.630-22");
        cliente.setTelefone("(11) 11111-1111");
        cliente.setNome("Inválido");
        return cliente;
    }

    private Cliente getCliente() {
        var cliente = new Cliente();
        cliente.setCpf("642.236.630-20");
        cliente.setTelefone("(11) 11111-1111");
        cliente.setNome("Danilo");
        return cliente;
    }
}
