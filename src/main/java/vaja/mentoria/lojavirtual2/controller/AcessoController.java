package vaja.mentoria.lojavirtual2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vaja.mentoria.lojavirtual2.model.Acesso;
import vaja.mentoria.lojavirtual2.repository.AcessoRepository;
import vaja.mentoria.lojavirtual2.service.AcessoService1;

@Controller
@RestController
@RequestMapping("/acesso")
public class AcessoController {

    @Autowired
    private AcessoRepository acessoRepository;
    
    
    @Autowired
    private AcessoService1 acessoService1; // Injeção do serviço AcessoService1
    
    


    @PostMapping("/salvarAcesso")
    public ResponseEntity<Acesso> salvarAcesso(@RequestBody Acesso acesso) {
        Acesso acessoSalvo = acessoService1.save(acesso); // Uso da instância injetada de AcessoService1
        return new ResponseEntity<>(acessoSalvo, HttpStatus.OK);
    }

    @PostMapping("/deleteAcesso")
    public ResponseEntity<String> deletarAcesso(@RequestBody Acesso acesso) {
        acessoRepository.deleteById(acesso.getId());
        
        return new ResponseEntity<>("Acesso Removido", HttpStatus.OK);
    }
    
    @Secured({"ROLE_GERENTE", "ROLE_ADMIN"})    
    @ResponseBody
    @DeleteMapping(value = "**/deleteAcessoPorId/{id}")
    public ResponseEntity<?> deleteAcessoPorId(@PathVariable("id") Long id) {
    	
    	acessoRepository.deleteById(id);
    	
    	return new ResponseEntity<>("Acesso Removido",HttpStatus.OK);
    	
    }
    }
  
