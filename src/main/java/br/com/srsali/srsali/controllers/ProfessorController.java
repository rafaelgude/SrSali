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
import br.com.srsali.srsali.models.Professor;
import br.com.srsali.srsali.repositories.InstituicaoDeEnsinoRepository;
import br.com.srsali.srsali.repositories.ProfessorRepository;
import br.com.srsali.srsali.repositories.UsuarioRepository;
import br.com.srsali.srsali.utils.DaoUtils;

@RestController
@RequestMapping("/professores")
public class ProfessorController {
	
    @Autowired ProfessorRepository professorRepo;
	@Autowired UsuarioRepository usuarioRepo;
	@Autowired InstituicaoDeEnsinoRepository instituicaoRepo;
	
	@GetMapping
	public ResponseEntity<Iterable<Professor>> listar() {
	    return ResponseEntity.ok().body(professorRepo.findAll());
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<Professor> buscar(@PathVariable long id) {
        return ResponseEntity.ok().body(DaoUtils.find(professorRepo, id, "Professor não encontrado."));
    }

	@PostMapping
	public ResponseEntity<Void> incluir(@RequestBody List<Professor> professores) {
	    professores.forEach(professor -> {
	        professor.setUsuario(professor.getUsuario() == null ? null : DaoUtils.find(usuarioRepo, professor.getUsuario().getId(), "Usuário não encontrado."));
	        professor.setInstituicao(DaoUtils.find(instituicaoRepo, 1, "Instituição não encontrada."));
	        professorRepo.save(professor);
	    });
		
		return ResponseEntity.created(null).build();
	}
	
	@PutMapping("/{id}")
    public ResponseEntity<Void> alterar(@PathVariable long id, @RequestBody Professor professor) {
	    var novoProfessor = DaoUtils.find(professorRepo, id, "Professor não encontrado.");
	    BeanUtils.copyProperties(professor, novoProfessor, "id", "instituicao");
	    professorRepo.save(novoProfessor);
	    
        return ResponseEntity.noContent().build();
    }
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable long id) {
	    try {
	        professorRepo.delete(DaoUtils.find(professorRepo, id, "Professor não encontrado."));
	    } catch (DataIntegrityViolationException e) {
	        throw new DataIntegrityException("Não é possível excluir um Professor que possua vínculo com outros cadastros.");
	    }
	    
	    return ResponseEntity.noContent().build();
	}
}