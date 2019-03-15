package br.com.app.restful.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.criteria.Predicate;

import br.com.app.restful.model.Client;

public class ClientDAO extends AbstractDAO<Client> implements DAO<Client> {

    final List<String> fields = Arrays.asList("name", "email");
    final List<Predicate> restrictions = new ArrayList<>();

    @Override
	public List<Client> list() {
		return listWithCriteria(fields, restrictions);
	}

}
