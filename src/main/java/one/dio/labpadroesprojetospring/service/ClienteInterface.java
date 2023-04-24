package one.dio.labpadroesprojetospring.service;

import one.dio.labpadroesprojetospring.model.Cliente;

public interface ClienteInterface {
    Iterable<Cliente> buscarTodos();

    Cliente buscarPorId(Long id) throws Exception;

    void inserir(Cliente cliente);

    void atualizar(Long id, Cliente cliente) throws Exception;

    void deletar(Long id) throws Exception;

}
