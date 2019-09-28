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

import br.com.srsali.srsali.models.Horario;
import br.com.srsali.srsali.services.HorarioService;

@RestController
@RequestMapping("/horarios")
public class HorarioController {
	
    @Autowired HorarioService horarioService;
	
    @GetMapping
    public ResponseEntity<Page<Horario>> findAll(@RequestParam(value="page", defaultValue="0") int page, 
                                                 @RequestParam(value="linesPerPage", defaultValue="24") int linesPerPage, 
                                                 @RequestParam(value="orderBy", defaultValue="turno,nome") String orderBy, 
                                                 @RequestParam(value="direction", defaultValue="ASC") String direction) {
        return ResponseEntity.ok().body(horarioService.findAll(page, linesPerPage, orderBy, direction));
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<Horario> find(@PathVariable int id) {
        return ResponseEntity.ok().body(horarioService.find(id));
    }
	
	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody List<Horario> horarios) {
	    horarios.forEach(horarioService::insert);
		return ResponseEntity.created(null).build();
	}
	
	@PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody Horario horario) {
	    var newHorario = horarioService.find(id);
	    BeanUtils.copyProperties(horario, newHorario, "id", "instituicao");
	    horarioService.update(newHorario);
	    
        return ResponseEntity.noContent().build();
    }
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable int id) {
	    horarioService.delete(id);
	    return ResponseEntity.noContent().build();
	}
}