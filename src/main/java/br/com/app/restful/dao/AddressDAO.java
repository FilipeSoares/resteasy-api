package br.com.app.restful.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.Predicate;
import javax.ws.rs.NotFoundException;

import br.com.app.restful.model.Address;

public class AddressDAO extends AbstractDAO<Address> implements DAO<Address> {

	public static final List<String> FIELDS = Arrays.asList("description", "street", "client");
	
	@Override
	public Address find(final Long id) throws NotFoundException {
		try {
			return findWithCriteria(id, FIELDS);
		} catch (NoResultException e) {
			throw new NotFoundException("Address not found");
		}
	}

	@Override
	public List<Address> list() {
		return listWithCriteria(FIELDS, restrictions);
	}

	public List<Address> list(final String street) {
		super.addLikeFilter("street", street);
		return list();
	}

}
