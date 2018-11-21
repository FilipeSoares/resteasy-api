package br.com.app.restful.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.transaction.Transactional;

public abstract class AbstractDAO<T> implements DAO<T> {
	
	@PersistenceContext(type=PersistenceContextType.EXTENDED)
	EntityManager em;
	
	private final Class<T> clazz;
	
	// @PostConstruct
	public AbstractDAO() {
		// this.clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		 Type type = getClass().getGenericSuperclass();

	        while (!(type instanceof ParameterizedType) || ((ParameterizedType) type).getRawType() != AbstractDAO.class) {
	            if (type instanceof ParameterizedType) {
	                type = ((Class<?>) ((ParameterizedType) type).getRawType()).getGenericSuperclass();
	            } else {
	                type = ((Class<?>) type).getGenericSuperclass();
	            }
	        }

	        this.clazz = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
	}
	
	@SuppressWarnings("unchecked")
	public List<T> listar() {
		return em.createQuery("select o from " + clazz.getSimpleName() + " o").getResultList();
	}
	
	public T buscar(Long id) {
		return em.find(clazz, id); 
	}
	
	@Transactional
	public T salvar(T entity) {
		return em.merge(entity);		
	}
	
	@Transactional
	public void remover(Long id) {
		em.remove(buscar(id));
	}

}
