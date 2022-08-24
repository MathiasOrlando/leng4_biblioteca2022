package com.biblioteca.session;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.biblioteca.entidad.Libro;
@Stateless


public class LibroSession {
	
	@PersistenceContext (name="BibliotecaPersistenceUnit")
	EntityManager em;
	
	
	public List<Libro> consultarLibros(){
		String jpql = "SELECT a FROM Libro a ORDER BY a.codigo";
		Query q = em.createQuery(jpql);
		List<Libro> libros = q.getResultList();
		return libros;
	}
	
	public List<Libro> consultarLibrosPorNombre(String nombre){
		String jpql = "SELECT a FROM Libro a WHERE UPPER(a.nombre) LIKE :n ORDER BY a.codigo";
		Query q = em.createQuery(jpql);
		q.setParameter("n", "%" + nombre.toUpperCase() + "%");
		List<Libro> libros = q.getResultList();
		return libros;
	}
	
	
	public Libro buscarPorCodigo (Integer codigo){
		return null;
	}
	
	
	// Inserta un libro en la bd.
	public Libro incluir (Libro libro){
		em.persist(libro); // insertar
		em.refresh(libro); // consulta dato insertado
		return libro;
	}
	
	public Libro editar (Libro libro){
		libro = em.merge(libro);
		return libro;
	}
	
	// Incluye o edita dependiendo de su existencia
	private Libro actualizarLibro (Libro libro) {
		Libro libroActualizado = null;
		Libro libroBuscar = buscarPorCodigo(libro.getCodigo());
		if (libroBuscar == null) {
			libroActualizado = incluir(libro);
		} else {
			libroActualizado = editar(libro);
		}
		return libroActualizado;
	}
	
	public void eliminar (Integer codigo){
		em.remove(codigo);
	
	}

}
