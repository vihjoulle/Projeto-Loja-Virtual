package vaja.mentoria.lojavirtual2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import vaja.mentoria.lojavirtual2.controller.AcessoController;
import vaja.mentoria.lojavirtual2.model.Acesso;
import vaja.mentoria.lojavirtual2.repository.AcessoRepository;

@Profile("test")
@SpringBootTest
public class LojaVirtualMentoria2ApplicationTests {

    @Autowired
    private AcessoController acessoController;

    @Autowired
    private AcessoRepository acessoRepository;

    @Autowired
    private WebApplicationContext wac;

    @Test
    public void testRestApiCadastroAcesso() throws JsonProcessingException, Exception {

        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        MockMvc mockMvc = builder.build();

        Acesso acesso = new Acesso();

        acesso.setDescricao("ROLE_COMPRADOR");

        ObjectMapper objectMapper = new ObjectMapper();

        ResultActions retornoApi = mockMvc
        		
                .perform(MockMvcRequestBuilders.post("/salvarAcesso")
                .content(objectMapper.writeValueAsString(acesso))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));
        
        System.out.println("Retorno da API"+ retornoApi.andReturn().getResponse().getContentAsString());
        
        Acesso objetoRetorno = objectMapper.
        		readValue(retornoApi.andReturn().getResponse().getContentAsString(), Acesso.class);
        
        assertEquals(acesso.getDescricao(), objetoRetorno.getDescricao());
    }

    @Test
    public void testCadastraAcesso() {

        Acesso acesso = new Acesso();
        acesso.setDescricao("ROLE_ADMIN");

        assertEquals(true, acesso.getId() == null);

        /* Esta gravando no banco */
        acesso = acessoController.salvarAcesso(acesso).getBody();

        assertEquals(true, acesso.getId() > 0);

        /* Validação dos dados no banco salvos de forma correta */
        assertEquals("ROLE_ADMIN", acesso.getDescricao());

        /* Teste de Carregamento */

        Acesso acesso2 = acessoRepository.findById(acesso.getId()).get();

        assertEquals(acesso.getId(), acesso2.getId());

        /* Teste de delete */

        acessoRepository.deleteById(acesso2.getId());

        acessoRepository.flush(); /* Roda esse sql de delete no banco de dados */

        Acesso acesso3 = acessoRepository.findById(acesso2.getId()).orElse(null);

        assertEquals(true, acesso3 == null);

        /* Teste de query */

        acesso = new Acesso();

        acesso.setDescricao("ROLE_ALUNO");

        acesso = acessoController.salvarAcesso(acesso).getBody();

        List<Acesso> acessos = acessoRepository.buscarAcessoDesc("ALUNO".trim().toUpperCase());

        assertEquals(1, acessos.size());

        acessoRepository.deleteById(acesso.getId());
    }
}
