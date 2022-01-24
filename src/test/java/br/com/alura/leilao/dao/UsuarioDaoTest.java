package br.com.alura.leilao.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import br.com.alura.leilao.util.UsuarioBuilder;



class UsuarioDaoTest {
	
	private UsuarioDao usuDao;
	private EntityManager em;
	
	@BeforeEach
	public void beforeEach() {
		this.em = JPAUtil.getEnitiEntityManager();
		this.usuDao = new UsuarioDao(em);
		em.getTransaction().begin();
	}
	
	@AfterEach
	public void afterEach() {
		em.getTransaction().rollback();
	}
	
	@Test
	void deveriaEncontrarUsuarioCadastrado() {
		Usuario usuario = new UsuarioBuilder()
				.comNome("Fulano")
				.comEmail("fulano@hotmail.com")
				.comSenha("123456")
				.criar();

		em.persist(usuario);
		
		Usuario usuarioEncontrado = this.usuDao.buscarPorUsername(usuario.getNome());

		assertNotNull(usuarioEncontrado);
	}

	@Test
	void naoDeveriaEncontrarUsuarioCadastrado() {
		Usuario usuario = new UsuarioBuilder()
				.comNome("Fulano")
				.comEmail("fulano@hotmail.com")
				.comSenha("123456")
				.criar();
		
		em.persist(usuario);
		
		assertThrows(NoResultException.class, () -> this.usuDao.buscarPorUsername("Beltrano"));
	}
	
	@Test
	void deveriaRemoverUmUsuario() {
		Usuario usuario = new UsuarioBuilder()
				.comNome("Fulano")
				.comEmail("fulano@hotmail.com")
				.comSenha("123456")
				.criar();

		em.persist(usuario);
		
		usuDao.deletar(usuario);
		
		assertThrows(NoResultException.class, () -> this.usuDao.buscarPorUsername("Beltrano"));
	}

}
