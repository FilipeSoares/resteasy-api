package br.com.app.restful.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.Predicate;
import javax.ws.rs.NotFoundException;

import br.com.app.restful.model.Address;

public class AddressDAO extends AbstractDAO<Address> implements DAO<Address> {

    final List<String> fields = Arrays.asList("description", "street");
    final List<Predicate> restrictions = new ArrayList<>();
    
    @Override
    public Address find(final Long id) throws NotFoundException {
    	try {
    		return findWithCriteria(id, fields);
    	} catch (NoResultException e) {
			throw new NotFoundException("Address not found");
		}
    }
    
    public List<Address> findByStreet(final String street) throws NotFoundException {
    	this.restrictions.add(builder.like(root.get("street"), "%" + street + "%"));
    	return listWithCriteria(this.fields, this.restrictions);
    }

    @Override
	public List<Address> list() {
		return listWithCriteria(fields, restrictions);
	}

}
