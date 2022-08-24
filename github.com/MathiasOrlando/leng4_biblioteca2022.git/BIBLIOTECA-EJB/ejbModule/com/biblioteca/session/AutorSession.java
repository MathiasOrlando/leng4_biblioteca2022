package com.biblioteca.session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.biblioteca.entidad.Autor;
@Stateless

public class AutorSession {
	
	@PersistenceContext (name="BibliotecaPersistenceUnit")
	EntityManager em;
	
	
	public List<Autor> consultarAutores(){
		String jpql = "SELECT a FROM Autor a ORDER BY a.codigo";
		Query q = em.createQuery(jpql);
		List<Autor> autores = q.getResultList();
		return autores;
	}
	
	public List<Autor> consultarAutoresPorNombreV1(String nombre){
		String jpql = "SELECT a FROM Autor a WHERE UPPER(a.nombre) LIKE :n ORDER BY a.codigo";
		Query q = em.createQuery(jpql);
		q.setParameter("n", "%" + nombre.toUpperCase() + "%");
		List<Autor> autores = q.getResultList();
		return autores;
	}
	
	public Map<String, Object> consultarAutoresPorNombre(String nombre){
		Map<String, Object> retorno  = new HashMap<String, Object>();
		try {
			String jpql = "SELECT a FROM Autor a WHERE UPPER(a.nombre) LIKE :n ORDER BY a.codigo";
			Query q = em.createQuery(jpql);
			q.setParameter("n", "%" + nombre.toUpperCase() + "%");
			List<Autor> autores = q.getResultList();
			retorno.put("success", true);
			retorno.put("result", autores);
			
		} catch (Exception e) {
			retorno.put("success", false);
			retorno.put("error", e.getMessage());
		}
		
		return retorno;
	}
	
	
	public Autor buscarPorCodigo (Integer codigo){
		return null;
	}
	
	
	// Inserta un autor en la bd.
	public Autor incluir (Autor autor){
		em.persist(autor); // insertar
		em.refresh(autor); // consulta dato insertado
		return autor;
	}
	
	public Autor editar (Autor autor){
		autor = em.merge(autor);
		return autor;
	}
	
	// Incluye o edita dependiendo de su existencia
	private Autor actualizarAutor (Autor autor) {
		Autor autorActualizado = null;
		Autor autorBuscar = buscarPorCodigo(autor.getCodigo());
		if (autorBuscar == null) {
			autorActualizado = incluir(autor);
		} else {
			autorActualizado = editar(autor);
		}
		return autorActualizado;
	}
	
	public void eliminar (Integer codigo){
		Autor autorBuscar = em.find(Autor.class, codigo);
		em.remove(autorBuscar);
		//Validar para eliminar cod no existente.
		// Sucess : false, error "autor 4 no existe"
	
	}
	
}
