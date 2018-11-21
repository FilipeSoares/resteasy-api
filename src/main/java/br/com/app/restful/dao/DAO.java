package br.com.app.restful.dao;

import java.util.List;

public interface DAO<T> {
	
	public List<T> listar();
	
	public T buscar(Long id);
	
	public T salvar(T entity);
	
	public void remover(Long id);
	
}
