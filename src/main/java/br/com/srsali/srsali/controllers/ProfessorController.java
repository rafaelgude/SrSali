package br.com.srsali.srsali.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.srsali.srsali.models.Professor;
import br.com.srsali.srsali.repositories.ProfessorRepository;
import br.com.srsali.srsali.repositories.UsuarioRepository;
import br.com.srsali.srsali.utils.DaoUtils;
import javassist.NotFoundException;

@RestController
@RequestMapping("/professor")
public class ProfessorController {
	
    @Autowired ProfessorRepository professorRepo;
	@Autowired UsuarioRepository usuarioRepo;

	@PostMapping
	public ResponseEntity<Void> incluir(@RequestBody Professor professor) {
		professor.setUsuario(usuarioRepo.findById(professor.getUsuario().getId()).orElse(null));
		professorRepo.save(professor);
		
		return ResponseEntity.created(null).build();
	}
	
	@PutMapping
    public ResponseEntity<Void> alterar(@RequestParam long id, @RequestBody Professor professor) throws NotFoundException {
	    var novoProfessor = DaoUtils.find(professorRepo, id, "Professor não encontrado.");
	    BeanUtils.copyProperties(professor, novoProfessor, "id");
	    professorRepo.save(novoProfessor);
	    
        return ResponseEntity.created(null).build();
    }
	
}