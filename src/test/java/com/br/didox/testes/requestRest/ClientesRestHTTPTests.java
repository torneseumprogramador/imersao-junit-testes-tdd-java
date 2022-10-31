package com.br.didox.testes.requestRest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.br.didox.testes.models.Cliente;
import com.br.didox.testes.requestRest.lib.HttpTestClient;

@SpringBootTest
public class ClientesRestHTTPTests {

    @Test
    public void buscaClientesControllerHTTP() throws ClientProtocolException, IOException{
        var statusCode = HttpTestClient.get("/clientes");
        assertEquals(200, statusCode);
    }

    @Test
    public void cadastrarClientesControllerHTTP() throws ClientProtocolException, IOException{
        var cliente = new Cliente();
        cliente.setCpf("642.236.630-20");
        cliente.setNome("Danilo");

        var statusCode = HttpTestClient.post("/clientes", cliente);
        assertEquals(201, statusCode);
    }
}
