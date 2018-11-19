package br.com.app.restful.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateful;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;

import br.com.app.restful.model.Cliente;

@Stateful
@ApplicationScoped
public class ClienteDAO {
	
	private List<Cliente> clientes = new ArrayList<Cliente>();
	
	public List<Cliente> listar() {
		return this.clientes;
	}
	
	public Cliente buscar(Long id) {
		Optional<Cliente> cliente = this.clientes.stream().filter(c -> c.getId().equals(id)).findAny();
		
		if (cliente.isPresent()) {
			return cliente.get();
		} else {
			throw new NotFoundException();
		}
		
	}
	
	public Long salvar(Cliente cliente) {
		cliente.setId(System.currentTimeMillis());
		this.clientes.add(cliente);
		return cliente.getId();
	}

}
