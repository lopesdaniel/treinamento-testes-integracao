package br.com.alura.leilao.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.junit.jupiter.api.Test;

import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;



class UsuarioDaoTest {
	
	private UsuarioDao usuDao;
	
	@Test
	void deveriaEncontrarUsuarioCadastrado() {
		EntityManager em = JPAUtil.getEnitiEntityManager();
		this.usuDao = new UsuarioDao(em);
		
		Usuario usuario = new Usuario("Cicrano", "cicrano@gmail.com", "12345");
		em.getTransaction().begin();
		em.persist(usuario);
		em.getTransaction().commit();
		
		Usuario usuarioEncontrado = this.usuDao.buscarPorUsername(usuario.getNome());
		assertNotNull(usuarioEncontrado);
	}

	@Test
	void naoDeveriaEncontrarUsuarioCadastrado() {
		EntityManager em = JPAUtil.getEnitiEntityManager();
		this.usuDao = new UsuarioDao(em);
		
		Usuario usuario = new Usuario("Fulano", "fulano@gmail.com", "123456");
		em.getTransaction().begin();
		em.persist(usuario);
		em.getTransaction().commit();
		
		assertThrows(NoResultException.class, () -> this.usuDao.buscarPorUsername("Beltrano"));
	}
	
	

}
