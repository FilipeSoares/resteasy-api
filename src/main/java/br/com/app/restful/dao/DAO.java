package br.com.app.restful.dao;

import java.util.List;

import javax.persistence.criteria.Predicate;

import br.com.app.restful.model.ModelEntity;

public interface DAO<T extends ModelEntity> {
	
	List<T> list();

	List<T> listWithCriteria(List<?> fields, List<Predicate> restrictions);
	
	T find(Long id);

	T findWithCriteria(final Long id, final List<?> fields);
	
	Long create(T entity);
	
	void update(T entity);
	
	void remove(Long id);
	
}
