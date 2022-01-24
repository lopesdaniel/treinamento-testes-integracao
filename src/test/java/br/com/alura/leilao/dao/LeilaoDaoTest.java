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
		Usuario usuario = criarUsuario();
		Leilao leilao = new Leilao("Notebook", new BigDecimal("2000"), LocalDate.now(), usuario);
		
		leilao = leilaoDao.salvar(leilao);
		
		Leilao leilaoSalvo = leilaoDao.buscarPorId(leilao.getId());
		
		assertNotNull(leilaoSalvo);
	}
	
	@Test
	void deveriaAtualizarUmLeilao() {
		Usuario usuario = criarUsuario();
		Leilao leilao = new Leilao("Notebook", new BigDecimal("2000"), LocalDate.now(), usuario);
		
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
