package br.com.srsali.srsali.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.srsali.srsali.models.InstituicaoDeEnsino;
import br.com.srsali.srsali.models.Usuario;
import br.com.srsali.srsali.services.InstituicaoDeEnsinoService;

@RestController
@RequestMapping("/instituicoes")
public class InstituicaoDeEnsinoController {
	
	@Autowired InstituicaoDeEnsinoService instituicaoService;

	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody Usuario criador) {
		instituicaoService.insert(criador);
		return ResponseEntity.created(null).build();
	}
	
	@PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody InstituicaoDeEnsino instituicao) {
	    var newInstituicao = instituicaoService.find(id);
	    BeanUtils.copyProperties(instituicao, newInstituicao, "id");
	    instituicaoService.update(newInstituicao);
	    
        return ResponseEntity.noContent().build();
    }
	
}