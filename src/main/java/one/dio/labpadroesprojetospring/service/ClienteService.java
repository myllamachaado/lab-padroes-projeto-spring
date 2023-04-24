package one.dio.labpadroesprojetospring.service;

import one.dio.labpadroesprojetospring.model.Cliente;
import one.dio.labpadroesprojetospring.model.Endereco;
import one.dio.labpadroesprojetospring.repository.ClienteRepository;
import one.dio.labpadroesprojetospring.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ClienteService implements ClienteInterface{

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ViaCepInterface viaCepInterface;

    public Iterable<Cliente> buscarTodos(){
        return clienteRepository.findAll();
    }

    public Cliente buscarPorId(Long id) throws Exception {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new Exception("Cliente não encontrado"));
    }

    public void inserir(Cliente cliente){
        this.salvaClienteComCep(cliente);
    }

    public void atualizar(Long id, Cliente cliente) throws Exception {
        Cliente clienteSalvo = this.buscarPorId(id);
        this.salvaClienteComCep(cliente);
    }

    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }

    private void salvaClienteComCep(Cliente cliente){
        String cep = cliente.getEndereco().getCep();
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
            Endereco novoEndereco = viaCepInterface.consultarCep(cep);
            enderecoRepository.save(novoEndereco);
            return novoEndereco;
        });
        cliente.setEndereco(endereco);
        clienteRepository.save(cliente);
    }
}
