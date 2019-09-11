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
import br.com.srsali.srsali.models.Turma;
import br.com.srsali.srsali.repositories.InstituicaoDeEnsinoRepository;
import br.com.srsali.srsali.repositories.TurmaRepository;
import br.com.srsali.srsali.utils.DaoUtils;

@RestController
@RequestMapping("/turmas")
public class TurmaController {
	
    @Autowired TurmaRepository turmaRepo;
	@Autowired InstituicaoDeEnsinoRepository instituicaoRepo;
	
	@GetMapping
	public ResponseEntity<Iterable<Turma>> listar() {
	    return ResponseEntity.ok().body(turmaRepo.findAll());
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<Turma> buscar(@PathVariable int id) {
        return ResponseEntity.ok().body(DaoUtils.find(turmaRepo, id, "Turma não encontrada."));
    }

	@PostMapping
	public ResponseEntity<Void> incluir(@RequestBody List<Turma> turmas) {
	    turmas.forEach(turmaRepo::save);
		return ResponseEntity.created(null).build();
	}
	
	@PutMapping("/{id}")
    public ResponseEntity<Void> alterar(@PathVariable int id, @RequestBody Turma turma) {
	    var novaTurma = DaoUtils.find(turmaRepo, id, "Turma não encontrada.");
	    BeanUtils.copyProperties(turma, novaTurma, "id");
	    turmaRepo.save(novaTurma);
	    
        return ResponseEntity.noContent().build();
    }
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable int id) {
	    try {
	        turmaRepo.delete(DaoUtils.find(turmaRepo, id, "Turma não encontradoa."));
	    } catch (DataIntegrityViolationException e) {
	        throw new DataIntegrityException("Não é possível excluir uma Turma que possui vínculo com outros cadastros.");
	    }
	    
	    return ResponseEntity.noContent().build();
	}
}