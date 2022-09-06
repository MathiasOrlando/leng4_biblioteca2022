package com.biblioteca.session;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
//import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

//import com.biblioteca.entidad.Cuidad;
import com.biblioteca.entidad.Libro;

@Stateless
public class LibroSession {

	@PersistenceContext (name = "BibliotecaPersistenceUnit")
	EntityManager em;
	
	//consultar libros
	public List<Libro> consultarLibros() {

		String jpql = "SELECT li FROM Ciudad li ORDER BY li.codigo";

		Query q = em.createQuery(jpql);
		List<Libro> libros = q.getResultList();

		return libros;
	}

	// consultar libros por nombre
	public List<Libro> consultarLibrosPorNombre(String nombre) {

		String jpql = "SELECT li FROM Autor li WHERE UPPER(li.nombre) LIKE : n ORDER BY li.codigo";

		Query q = em.createQuery(jpql);
		q.setParameter("n", "%" + nombre.toUpperCase() + "%");
		List<Libro> libros = q.getResultList();
		return libros;
	}

	// buscar el libro por su codigo
	public Libro buscarPorCodigo(Integer codigo) {
		Libro libros = em.find(Libro.class, codigo);
		return libros;
	}

	public Libro editar(Libro libro) {

		libro = em.merge(libro);
		return libro;
	}

	// Insertar un libro en la BD Utilizando em (EntityManager)
	public Libro incluir(Libro libro) {

		em.persist(libro); // insertar
		em.refresh(libro); // consulta dato insertado
		return libro;		
	}

	// Incluye o edita una libro dependiendo de si existe o no
	private Libro actualizar(Libro libro) {
		Libro libroActualizado = null;
		Libro libroBuscar = buscarPorCodigo(libro.getCodigo());
		if (libroBuscar == null) {
			libroActualizado = incluir(libro);
		} else {
			libroActualizado = editar(libro);
		}
		return libroActualizado;

	}

	public void eliminar(Integer codigo) {

		em.remove(codigo); // se le pasa el codigo para eliminar en la bd

	}

}
