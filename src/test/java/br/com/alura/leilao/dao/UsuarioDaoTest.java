package br.com.alura.leilao.dao;

import static org.junit.Assert.assertNotNull;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;

import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;



class UsuarioDaoTest {
	
	private UsuarioDao usuDao;
	
	@Test
	void testeBuscarUsuarioPeloUsername() {
		EntityManager em = JPAUtil.getEnitiEntityManager();
		this.usuDao = new UsuarioDao(em);
		
		Usuario usuario = new Usuario("Cicrano", "cicrano@gmail.com", "12345");
		em.getTransaction().begin();
		em.persist(usuario);
		em.getTransaction().commit();
		
		Usuario usuarioEncontrado = this.usuDao.buscarPorUsername(usuario.getNome());
		assertNotNull(usuarioEncontrado);
	}

}
