package br.com.app.restful.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.Predicate;
import javax.ws.rs.NotFoundException;

import br.com.app.restful.model.Client;

public class ClientDAO extends AbstractDAO<Client> implements DAO<Client> {

    final List<String> fields = Arrays.asList("name", "email");
    final List<Predicate> restrictions = new ArrayList<>();
    
    @Override
    public Client find(final Long id) throws NotFoundException {
    	try {
    		return findWithCriteria(id, fields);
    	} catch (NoResultException e) {
			throw new NotFoundException("Client not found!");
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

}
