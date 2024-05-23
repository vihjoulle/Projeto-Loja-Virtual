package vaja.mentoria.lojavirtual2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vaja.mentoria.lojavirtual2.model.Acesso;
import vaja.mentoria.lojavirtual2.repository.AcessoRepository;

@Controller
@RestController
@RequestMapping("/acesso")
public class AcessoController {
	
	@Autowired
	private AcessoRepository acessoRepository;

	@ResponseBody
	@PostMapping("/salvarAcesso")
	public ResponseEntity<Acesso> salvarAcesso(@RequestBody Acesso acesso) {
		Acesso salvo = acessoRepository.save(acesso);
		return ResponseEntity.ok(salvo);
	}

	@ResponseBody
	@PostMapping("/deleteAcesso")
	public ResponseEntity<String> deletarAcesso(@RequestBody Acesso acesso) {
		acessoRepository.deleteById(acesso.getId());
		return new ResponseEntity<>("Acesso Removido", HttpStatus.OK);
	}
}
