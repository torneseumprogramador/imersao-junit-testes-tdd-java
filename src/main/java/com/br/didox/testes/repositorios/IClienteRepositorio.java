package com.br.didox.testes.repositorios;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.br.didox.testes.models.Cliente;

public interface IClienteRepositorio extends CrudRepository<Cliente, Integer> {
  @Query(value="select * from clientes where nome = :nome", nativeQuery = true)
  public Cliente findByName(String nome);
}
