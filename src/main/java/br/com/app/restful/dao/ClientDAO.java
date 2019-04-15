package br.com.app.restful.dao;

import java.util.Arrays;
import java.util.List;

import javax.persistence.NoResultException;
import javax.ws.rs.NotFoundException;

import br.com.app.restful.model.Client;

public class ClientDAO extends AbstractDAO<Client> implements DAO<Client> {

	final List<String> fields = Arrays.asList("name", "email");

	@Override
	public Client find(final Long id) throws NotFoundException {
		try {
			return findWithCriteria(id, fields);
		} catch (NoResultException e) {
			throw new NotFoundException("Client not found");
		}
	}

	public List<Client> findByEmail(final String email) throws NotFoundException {
		this.restrictions.add(builder.equal(root.get("email"), email));
		return listWithCriteria(this.fields, this.restrictions);
	}

	@Override
	public List<Client> list() {
		return listWithCriteria(fields, restrictions);
	}

	public List<Client> list(final String name, final String email) {

		super.addLikeFilter("name", name);
		super.addLikeFilter("email", email);
		return list();
	}

}
