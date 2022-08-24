package com.biblioteca.session;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.biblioteca.entidad.Ciudad;
import com.biblioteca.entidad.Ciudad;

public class CiudadSession {
	
	@PersistenceContext (name="BibliotecaPersistenceUnit")
	EntityManager em;
	
	
	public List<Ciudad> consultarCiudades(){
		String jpql = "SELECT a FROM Ciudad a ORDER BY a.codigo";
		Query q = em.createQuery(jpql);
		List<Ciudad> ciudades = q.getResultList();
		return ciudades;
	}
	
	public List<Ciudad> consultarCiudadesPorNombre(String nombre){
		String jpql = "SELECT a FROM Ciudad a WHERE UPPER(a.nombre) LIKE :n ORDER BY a.codigo";
		Query q = em.createQuery(jpql);
		q.setParameter("n", "%" + nombre.toUpperCase() + "%");
		List<Ciudad> ciudades = q.getResultList();
		return ciudades;
	}
	
	
	public Ciudad buscarPorCodigo (Integer codigo){
		return null;
	}
	
	
	// Inserta un Ciudad en la bd.
	public Ciudad incluir (Ciudad ciudad){
		em.persist(ciudad); // insertar
		em.refresh(ciudad); // consulta dato insertado
		return ciudad;
	}
	
	public Ciudad editar (Ciudad ciudad){
		ciudad = em.merge(ciudad);
		return ciudad;
	}
	
	// Incluye o edita dependiendo de su existencia
	private Ciudad actualizarCiudad (Ciudad ciudad) {
		Ciudad ciudadActualizado = null;
		Ciudad ciudadBuscar = buscarPorCodigo(ciudad.getCodigo());
		if (ciudadBuscar == null) {
			ciudadActualizado = incluir(ciudad);
		} else {
			ciudadActualizado = editar(ciudad);
		}
		return ciudadActualizado;
	}
	
	public void eliminar (Integer codigo){
		em.remove(codigo);
	
	}

}
