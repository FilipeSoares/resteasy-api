package br.com.app.restful.dao;

import java.util.List;

import br.com.app.restful.model.ModelEntity;

public interface DAO<T extends ModelEntity> {
	
	List<T> list();
	
	T find(Long id);
	
	Long create(T entity);
	
	void update(T entity);
	
	void remove(Long id);
	
}
