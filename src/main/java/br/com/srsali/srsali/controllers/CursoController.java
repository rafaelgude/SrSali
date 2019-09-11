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
import br.com.srsali.srsali.models.Curso;
import br.com.srsali.srsali.repositories.CursoRepository;
import br.com.srsali.srsali.repositories.InstituicaoDeEnsinoRepository;
import br.com.srsali.srsali.repositories.UsuarioRepository;
import br.com.srsali.srsali.utils.DaoUtils;

@RestController
@RequestMapping("/cursos")
public class CursoController {
	
    @Autowired CursoRepository cursoRepo;
	@Autowired UsuarioRepository usuarioRepo;
	@Autowired InstituicaoDeEnsinoRepository instituicaoRepo;
	
	@GetMapping
	public ResponseEntity<Iterable<Curso>> listar() {
	    return ResponseEntity.ok().body(cursoRepo.findAll());
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<Curso> buscar(@PathVariable int id) {
        return ResponseEntity.ok().body(DaoUtils.find(cursoRepo, id, "Curso não encontrado."));
    }

	@PostMapping
	public ResponseEntity<Void> incluir(@RequestBody List<Curso> cursos) {
	    cursos.forEach(curso -> {
	        curso.setInstituicao(DaoUtils.find(instituicaoRepo, 1, "Instituição não encontrada."));
	        cursoRepo.save(curso);
	    });
		
		return ResponseEntity.created(null).build();
	}
	
	@PutMapping("/{id}")
    public ResponseEntity<Void> alterar(@PathVariable int id, @RequestBody Curso curso) {
	    var novoCurso = DaoUtils.find(cursoRepo, id, "Curso não encontrado.");
	    BeanUtils.copyProperties(curso, novoCurso, "id", "instituicao");
	    cursoRepo.save(novoCurso);
	    
        return ResponseEntity.noContent().build();
    }
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable int id) {
	    try {
	        cursoRepo.delete(DaoUtils.find(cursoRepo, id, "Curso não encontrado."));
	    } catch (DataIntegrityViolationException e) {
	        throw new DataIntegrityException("Não é possível excluir um Curso que possui vínculo com outros cadastros.");
	    }
	    
	    return ResponseEntity.noContent().build();
	}
}