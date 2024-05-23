package vaja.mentoria.lojavirtual2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import vaja.mentoria.lojavirtual2.controller.AcessoController;
import vaja.mentoria.lojavirtual2.model.Acesso;
import vaja.mentoria.lojavirtual2.repository.AcessoRepository;

@SpringBootTest
public class LojaVirtualMentoria2ApplicationTests {
	
	@Autowired
	private AcessoController acessoController;
	
	private Object Acesso;
	
	private AcessoRepository AcessoRepository;

	private CrudRepository<vaja.mentoria.lojavirtual2.model.Acesso, Long> acessoRepository;

	@Test
	public void testCadastraAcesso() {
		
		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_ADMIN");
		
		assertEquals(true, acesso.getId() == null);

		/*Esta gravando no banco*/
		acesso = acessoController.salvarAcesso(acesso).getBody();
		
		assertEquals(true, acesso.getId() > 0);
		
		/*Validação dos dados no banco salvos de forma correta*/
		assertEquals("ROLE_ADMIN", acesso.getDescricao());
		
		/* Teste de Carregamento*/
		
		Acesso acesso2 = AcessoRepository.findById(acesso.getId()).get();
		
		assertEquals(acesso.getId(), acesso2.getId() );
		
		/*Teste de delete*/
		
		AcessoRepository.deleteById(acesso2.getId());
		
		AcessoRepository.flush();/*Roda esse sql de delete no banco de dados*/
		
		Acesso acesso3 = AcessoRepository.findById(acesso2.getId()).orElse(null);
		
		assertEquals(true, acesso3 == null);
		
		
		/*Teste de query*/
		
		acesso = new Acesso();
		
		acesso.setDescricao("ROLE_ALUNO");
		
				
		acesso = acessoController.salvarAcesso(acesso).getBody();
		
		List<Acesso> acessos = AcessoRepository.buscarAcessoDesc("ALUNO".trim().toUpperCase());
		
		assertEquals(1, acessos.size());
		
		acessoRepository.deleteById(acesso.getId());
		
		
	}
}
