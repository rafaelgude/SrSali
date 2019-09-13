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

import br.com.srsali.srsali.models.Disciplina;
import br.com.srsali.srsali.services.DisciplinaService;

@RestController
@RequestMapping("/disciplinas")
public class DisciplinaController {
	
    @Autowired DisciplinaService disciplinaService;
	
	@GetMapping
	public ResponseEntity<List<Disciplina>> findAll() {
	    return ResponseEntity.ok().body(disciplinaService.findAll());
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<Disciplina> find(@PathVariable int id) {
        return ResponseEntity.ok().body(disciplinaService.find(id));
    }
	
	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody List<Disciplina> disciplinas) {
	    disciplinas.forEach(disciplinaService::insert);
		return ResponseEntity.created(null).build();
	}
	
	@PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody Disciplina disciplina) {
	    var newDisciplina = disciplinaService.find(id);
	    BeanUtils.copyProperties(disciplina, newDisciplina, "id", "instituicao");
	    disciplinaService.update(newDisciplina);
	    
        return ResponseEntity.noContent().build();
    }
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable int id) {
	    disciplinaService.delete(id);
	    return ResponseEntity.noContent().build();
	}
}