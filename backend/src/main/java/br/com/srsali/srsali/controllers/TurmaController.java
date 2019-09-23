package br.com.srsali.srsali.controllers;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.srsali.srsali.models.Turma;
import br.com.srsali.srsali.services.TurmaService;

@RestController
@RequestMapping("/turmas")
public class TurmaController {
	
    @Autowired TurmaService turmaService;
	
    @GetMapping
    public ResponseEntity<Page<Turma>> findAll(@RequestParam(value="page", defaultValue="0") int page, 
                                               @RequestParam(value="linesPerPage", defaultValue="24") int linesPerPage, 
                                               @RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
                                               @RequestParam(value="direction", defaultValue="ASC") String direction) {
        return ResponseEntity.ok().body(turmaService.findAll(page, linesPerPage, orderBy, direction));
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<Turma> find(@PathVariable int id) {
        return ResponseEntity.ok().body(turmaService.find(id));
    }
	
	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody List<Turma> turmas) {
	    turmas.forEach(turmaService::insert);
		return ResponseEntity.created(null).build();
	}
	
	@PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody Turma turma) {
	    var newTurma = turmaService.find(id);
	    BeanUtils.copyProperties(turma, newTurma, "id", "instituicao");
	    turmaService.update(newTurma);
	    
        return ResponseEntity.noContent().build();
    }
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable int id) {
	    turmaService.delete(id);
	    return ResponseEntity.noContent().build();
	}
}