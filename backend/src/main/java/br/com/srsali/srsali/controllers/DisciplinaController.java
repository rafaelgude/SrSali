package br.com.srsali.srsali.controllers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
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

import br.com.srsali.srsali.models.Disciplina;
import br.com.srsali.srsali.services.DisciplinaService;
import br.com.srsali.srsali.services.TurmaService;

@RestController
@RequestMapping("/disciplinas")
public class DisciplinaController {
	
    @Autowired DisciplinaService disciplinaService;
    @Autowired TurmaService turmaService;
	
    @GetMapping
    public ResponseEntity<Page<Disciplina>> findAll(@RequestParam(value="page", defaultValue="0") int page, 
                                                    @RequestParam(value="linesPerPage", defaultValue="24") int linesPerPage, 
                                                    @RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
                                                    @RequestParam(value="direction", defaultValue="ASC") String direction,
                                                    @RequestParam(value="turmasId", required = false) String turmasId) {
        if (turmasId == null || turmasId.isBlank())
            return ResponseEntity.ok().body(disciplinaService.findAll(page, linesPerPage, orderBy, direction));
        else {
            var disciplinas = new ArrayList<Disciplina>();
            List.of(turmasId.split(",")).stream().forEach(x -> disciplinas.addAll(turmaService.find(Integer.parseInt(x)).getCurso().getDisciplinas()));
            var disciplinasOrdered = disciplinas.stream().distinct().sorted(Comparator.comparing(Disciplina::getNome)).collect(Collectors.toList());
            return ResponseEntity.ok().body(new PageImpl<Disciplina>(disciplinasOrdered, PageRequest.of(page, linesPerPage, Direction.valueOf(direction.toUpperCase()), orderBy), disciplinasOrdered.size()));
        }
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