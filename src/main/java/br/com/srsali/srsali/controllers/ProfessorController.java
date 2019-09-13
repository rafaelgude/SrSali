package br.com.srsali.srsali.controllers;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.srsali.srsali.models.Professor;
import br.com.srsali.srsali.services.ProfessorService;

@RestController
@RequestMapping("/professores")
public class ProfessorController {
	
    @Autowired ProfessorService professorService;
	
	@GetMapping
	public ResponseEntity<List<Professor>> findAll() {
	    return ResponseEntity.ok().body(professorService.findAll());
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<Professor> find(@PathVariable int id) {
        return ResponseEntity.ok().body(professorService.find(id));
    }
	
	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody List<Professor> professores) {
	    professores.forEach(professorService::insert);
		return ResponseEntity.created(null).build();
	}
	
	@PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody Professor professor) {
	    var newProfessor = professorService.find(id);
	    BeanUtils.copyProperties(professor, newProfessor, "id", "instituicao");
	    professorService.update(newProfessor);
	    
        return ResponseEntity.noContent().build();
    }
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable int id) {
	    professorService.delete(id);
	    return ResponseEntity.noContent().build();
	}
}