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

import br.com.srsali.srsali.models.Ferramenta;
import br.com.srsali.srsali.services.FerramentaService;

@RestController
@RequestMapping("/ferramentas")
public class FerramentaController {
	
    @Autowired FerramentaService ferramentaService;
	
	@GetMapping
	public ResponseEntity<List<Ferramenta>> findAll() {
	    return ResponseEntity.ok().body(ferramentaService.findAll());
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<Ferramenta> find(@PathVariable int id) {
        return ResponseEntity.ok().body(ferramentaService.find(id));
    }
	
	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody List<Ferramenta> ferramentas) {
	    ferramentas.forEach(ferramentaService::insert);
		return ResponseEntity.created(null).build();
	}
	
	@PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody Ferramenta ferramenta) {
	    var newFerramenta = ferramentaService.find(id);
	    BeanUtils.copyProperties(ferramenta, newFerramenta, "id", "instituicao");
	    ferramentaService.update(newFerramenta);
	    
        return ResponseEntity.noContent().build();
    }
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable int id) {
	    ferramentaService.delete(id);
	    return ResponseEntity.noContent().build();
	}
}