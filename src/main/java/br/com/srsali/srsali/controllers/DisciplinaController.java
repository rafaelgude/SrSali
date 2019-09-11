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
import br.com.srsali.srsali.models.Disciplina;
import br.com.srsali.srsali.repositories.DisciplinaRepository;
import br.com.srsali.srsali.repositories.InstituicaoDeEnsinoRepository;
import br.com.srsali.srsali.utils.DaoUtils;

@RestController
@RequestMapping("/disciplinas")
public class DisciplinaController {
	
    @Autowired DisciplinaRepository disciplinaRepo;
	@Autowired InstituicaoDeEnsinoRepository instituicaoRepo;
	
	@GetMapping
	public ResponseEntity<Iterable<Disciplina>> listar() {
	    return ResponseEntity.ok().body(disciplinaRepo.findAll());
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<Disciplina> buscar(@PathVariable int id) {
        return ResponseEntity.ok().body(DaoUtils.find(disciplinaRepo, id, "Disciplina não encontrada."));
    }

	@PostMapping
	public ResponseEntity<Void> incluir(@RequestBody List<Disciplina> disciplinas) {
	    disciplinas.forEach(disciplina -> {
	        disciplina.setInstituicao(DaoUtils.find(instituicaoRepo, 1, "Instituição não encontrada."));
	        disciplinaRepo.save(disciplina);
	    });
		
		return ResponseEntity.created(null).build();
	}
	
	@PutMapping("/{id}")
    public ResponseEntity<Void> alterar(@PathVariable int id, @RequestBody Disciplina disciplina) {
	    var novaDisciplina = DaoUtils.find(disciplinaRepo, id, "Disciplina não encontrada.");
	    BeanUtils.copyProperties(disciplina, novaDisciplina, "id", "instituicao");
	    disciplinaRepo.save(novaDisciplina);
	    
        return ResponseEntity.noContent().build();
    }
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable int id) {
	    try {
	        disciplinaRepo.delete(DaoUtils.find(disciplinaRepo, id, "Disciplina não encontradoa."));
	    } catch (DataIntegrityViolationException e) {
	        throw new DataIntegrityException("Não é possível excluir uma Disciplina que possui vínculo com outros cadastros.");
	    }
	    
	    return ResponseEntity.noContent().build();
	}
}