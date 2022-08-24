package com.biblioteca.session;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.biblioteca.entidad.Cliente;

public class ClienteSession {
	
	@PersistenceContext (name="BibliotecaPersistenceUnit")
	EntityManager em;
	
	
	public List<Cliente> consultarClientes(){
		String jpql = "SELECT a FROM Cliente a ORDER BY a.codigo";
		Query q = em.createQuery(jpql);
		List<Cliente> ciudades = q.getResultList();
		return ciudades;
	}
	
	public List<Cliente> consultarClientesPorNombre(String nombre){
		String jpql = "SELECT a FROM Cliente a WHERE UPPER(a.nombre) LIKE :n ORDER BY a.codigo";
		Query q = em.createQuery(jpql);
		q.setParameter("n", "%" + nombre.toUpperCase() + "%");
		List<Cliente> ciudades = q.getResultList();
		return ciudades;
	}
	
	
	public Cliente buscarPorCodigo (Integer codigo){
		return null;
	}
	
	
	// Inserta un Cliente en la bd.
	public Cliente incluir (Cliente ciudad){
		em.persist(ciudad); // insertar
		em.refresh(ciudad); // consulta dato insertado
		return ciudad;
	}
	
	public Cliente editar (Cliente ciudad){
		ciudad = em.merge(ciudad);
		return ciudad;
	}
	
	// Incluye o edita dependiendo de su existencia
	private Cliente actualizarCliente (Cliente cliente) {
		Cliente clienteActualizado = null;
		Cliente clienteBuscar = buscarPorCodigo(cliente.getCodigo());
		if (clienteBuscar == null) {
			clienteActualizado = incluir(cliente);
		} else {
			clienteActualizado = editar(cliente);
		}
		return clienteActualizado;
	}
	
	public void eliminar (Integer codigo){
		em.remove(codigo);
	
	}


}
