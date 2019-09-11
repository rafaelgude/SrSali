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
import br.com.srsali.srsali.models.Horario;
import br.com.srsali.srsali.repositories.HorarioRepository;
import br.com.srsali.srsali.repositories.InstituicaoDeEnsinoRepository;
import br.com.srsali.srsali.repositories.UsuarioRepository;
import br.com.srsali.srsali.utils.DaoUtils;

@RestController
@RequestMapping("/horarios")
public class HorarioController {
	
    @Autowired HorarioRepository horarioRepo;
	@Autowired UsuarioRepository usuarioRepo;
	@Autowired InstituicaoDeEnsinoRepository instituicaoRepo;
	
	@GetMapping
	public ResponseEntity<Iterable<Horario>> listar() {
	    return ResponseEntity.ok().body(horarioRepo.findAll());
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<Horario> buscar(@PathVariable int id) {
        return ResponseEntity.ok().body(DaoUtils.find(horarioRepo, id, "Horario não encontrado."));
    }

	@PostMapping
	public ResponseEntity<Void> incluir(@RequestBody List<Horario> horarios) {
	    horarios.forEach(horario -> {
	        horario.setInstituicao(DaoUtils.find(instituicaoRepo, 1, "Instituição não encontrada."));
	        horarioRepo.save(horario);
	    });
		
		return ResponseEntity.created(null).build();
	}
	
	@PutMapping("/{id}")
    public ResponseEntity<Void> alterar(@PathVariable int id, @RequestBody Horario horario) {
	    var novoHorario = DaoUtils.find(horarioRepo, id, "Horario não encontrado.");
	    BeanUtils.copyProperties(horario, novoHorario, "id", "instituicao");
	    horarioRepo.save(novoHorario);
	    
        return ResponseEntity.noContent().build();
    }
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable int id) {
	    try {
	        horarioRepo.delete(DaoUtils.find(horarioRepo, id, "Horario não encontrado."));
	    } catch (DataIntegrityViolationException e) {
	        throw new DataIntegrityException("Não é possível excluir um Horario que possui vínculo com outros cadastros.");
	    }
	    
	    return ResponseEntity.noContent().build();
	}
}