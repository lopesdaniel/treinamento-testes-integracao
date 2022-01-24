package br.com.alura.leilao.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import br.com.alura.leilao.util.LeilaoBuilder;
import br.com.alura.leilao.util.UsuarioBuilder;



class LeilaoDaoTest {
	
	private LeilaoDao leilaoDao;
	private EntityManager em;
	
	@BeforeEach
	public void beforeEach() {
		this.em = JPAUtil.getEnitiEntityManager();
		this.leilaoDao = new LeilaoDao(em);
		em.getTransaction().begin();
	}
	
	@AfterEach
	public void afterEach() {
		em.getTransaction().rollback();
	}
	
	@Test
	void deveriaCadastrarUmLeilao() {
		Usuario usuario = new UsuarioBuilder()
			.comNome("Fulano")
			.comEmail("fulano@hotmail.com")
			.comSenha("123456")
			.criar();
		
		em.persist(usuario);
		
		Leilao leilao = new LeilaoBuilder().comNome("Notebook")
											.comValorInicial("2000")
											.comData(LocalDate.now())
											.comUsuario(usuario)
											.criar();
		
		leilao = leilaoDao.salvar(leilao);
		
		Leilao leilaoSalvo = leilaoDao.buscarPorId(leilao.getId());
		
		assertNotNull(leilaoSalvo);
	}
	
	@Test
	void deveriaAtualizarUmLeilao() {
		Usuario usuario = new UsuarioBuilder()
								.comNome("Fulano")
								.comEmail("fulano@hotmail.com")
								.comSenha("123456")
								.criar();
			
		em.persist(usuario);
			
		Leilao leilao = new LeilaoBuilder()
							.comNome("Notebook")
							.comValorInicial("2000")
							.comData(LocalDate.now())
							.comUsuario(usuario)
							.criar();
		
		leilao = leilaoDao.salvar(leilao);
		
		leilao.setNome("Mouse");
		leilao.setValorInicial(new BigDecimal("20"));
		
		leilao = leilaoDao.salvar(leilao);
		
		Leilao leilaoSalvo = leilaoDao.buscarPorId(leilao.getId());
		
		assertEquals("Mouse", leilaoSalvo.getNome());
		assertEquals(new BigDecimal("20"), leilaoSalvo.getValorInicial());
	}
	
	private Usuario criarUsuario(){
		Usuario usuario = new Usuario("Fulano", "fulano@gmail.com", "123456");

		em.persist(usuario);
		
		return usuario;
	}

}
