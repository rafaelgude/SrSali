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

import br.com.srsali.srsali.models.Curso;
import br.com.srsali.srsali.services.CursoService;

@RestController
@RequestMapping("/cursos")
public class CursoController {
    
    @Autowired CursoService cursoService;
    
    @GetMapping
    public ResponseEntity<List<Curso>> findAll() {
        return ResponseEntity.ok().body(cursoService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Curso> find(@PathVariable int id) {
        return ResponseEntity.ok().body(cursoService.find(id));
    }
    
    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody List<Curso> cursos) {
        cursos.forEach(cursoService::insert);
        return ResponseEntity.created(null).build();
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody Curso curso) {
        var newCurso = cursoService.find(id);
        BeanUtils.copyProperties(curso, newCurso, "id", "instituicao");
        cursoService.update(newCurso);
        
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        cursoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}