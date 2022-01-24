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
		Usuario usuario = criarUsuario();
		Usuario usuarioEncontrado = this.usuDao.buscarPorUsername(usuario.getNome());

		assertNotNull(usuarioEncontrado);
	}

	@Test
	void naoDeveriaEncontrarUsuarioCadastrado() {
		criarUsuario();
		
		assertThrows(NoResultException.class, () -> this.usuDao.buscarPorUsername("Beltrano"));
	}
	
	@Test
	void deveriaRemoverUmUsuario() {
		Usuario usuario = criarUsuario();
		usuDao.deletar(usuario);
		
		assertThrows(NoResultException.class, () -> this.usuDao.buscarPorUsername("Beltrano"));
	}
	
	private Usuario criarUsuario(){
		Usuario usuario = new Usuario("Fulano", "fulano@gmail.com", "123456");

		em.persist(usuario);
		
		return usuario;
	}

}
