package br.com.srsali.srsali.controllers;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.srsali.srsali.exceptions.DataIntegrityException;
import br.com.srsali.srsali.models.Ferramenta;
import br.com.srsali.srsali.repositories.FerramentaRepository;
import br.com.srsali.srsali.repositories.InstituicaoDeEnsinoRepository;
import br.com.srsali.srsali.utils.DaoUtils;

@RestController
@RequestMapping("/ferramentas")
public class FerramentaController {
	
    @Autowired FerramentaRepository ferramentaRepo;
	@Autowired InstituicaoDeEnsinoRepository instituicaoRepo;
	
	@GetMapping
	public ResponseEntity<Iterable<Ferramenta>> listar() {
	    return ResponseEntity.ok().body(ferramentaRepo.findAll());
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<Ferramenta> buscar(@PathVariable long id) {
        return ResponseEntity.ok().body(DaoUtils.find(ferramentaRepo, id, "Ferramenta não encontrada."));
    }

	@PostMapping
	public ResponseEntity<Void> incluir(@RequestBody List<Ferramenta> ferramentas) {
	    ferramentas.forEach(ferramenta -> {
	        ferramenta.setInstituicao(DaoUtils.find(instituicaoRepo, 1, "Instituição não encontrada."));
	        ferramentaRepo.save(ferramenta);
	    });
		
		return ResponseEntity.created(null).build();
	}
	
	@PutMapping("/{id}")
    public ResponseEntity<Void> alterar(@PathVariable long id, @RequestBody Ferramenta ferramenta) {
	    var novaFerramenta = DaoUtils.find(ferramentaRepo, id, "Ferramenta não encontrada.");
	    BeanUtils.copyProperties(ferramenta, novaFerramenta, "id", "instituicao");
	    ferramentaRepo.save(novaFerramenta);
	    
        return ResponseEntity.noContent().build();
    }
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable long id) {
	    try {
	        ferramentaRepo.delete(DaoUtils.find(ferramentaRepo, id, "Ferramenta não encontradoa."));
	    } catch (DataIntegrityViolationException e) {
	        throw new DataIntegrityException("Não é possível excluir uma Ferramenta que possui vínculo com outros cadastros.");
	    }
	    
	    return ResponseEntity.noContent().build();
	}
}