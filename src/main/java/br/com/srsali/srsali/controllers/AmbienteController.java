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
import br.com.srsali.srsali.models.Ambiente;
import br.com.srsali.srsali.repositories.AmbienteRepository;
import br.com.srsali.srsali.repositories.InstituicaoDeEnsinoRepository;
import br.com.srsali.srsali.repositories.UsuarioRepository;
import br.com.srsali.srsali.utils.DaoUtils;

@RestController
@RequestMapping("/ambientes")
public class AmbienteController {
	
    @Autowired AmbienteRepository ambienteRepo;
	@Autowired UsuarioRepository usuarioRepo;
	@Autowired InstituicaoDeEnsinoRepository instituicaoRepo;
	
	@GetMapping
	public ResponseEntity<Iterable<Ambiente>> listar() {
	    return ResponseEntity.ok().body(ambienteRepo.findAll());
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<Ambiente> buscar(@PathVariable long id) {
        return ResponseEntity.ok().body(DaoUtils.find(ambienteRepo, id, "Ambiente não encontrado."));
    }

	@PostMapping
	public ResponseEntity<Void> incluir(@RequestBody List<Ambiente> ambientes) {
	    ambientes.forEach(ambiente -> {
	        ambiente.setInstituicao(DaoUtils.find(instituicaoRepo, 1, "Instituição não encontrada."));
	        ambienteRepo.save(ambiente);
	    });
		
		return ResponseEntity.created(null).build();
	}
	
	@PutMapping("/{id}")
    public ResponseEntity<Void> alterar(@PathVariable long id, @RequestBody Ambiente ambiente) {
	    var novoAmbiente = DaoUtils.find(ambienteRepo, id, "Ambiente não encontrado.");
	    BeanUtils.copyProperties(ambiente, novoAmbiente, "id", "instituicao");
	    ambienteRepo.save(novoAmbiente);
	    
        return ResponseEntity.noContent().build();
    }
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable long id) {
	    try {
	        ambienteRepo.delete(DaoUtils.find(ambienteRepo, id, "Ambiente não encontrado."));
	    } catch (DataIntegrityViolationException e) {
	        throw new DataIntegrityException("Não é possível excluir um Ambiente que possui vínculo com outros cadastros.");
	    }
	    
	    return ResponseEntity.noContent().build();
	}
}