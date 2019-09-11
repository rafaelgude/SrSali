package br.com.srsali.srsali.controllers;

import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.srsali.srsali.enums.Funcao;
import br.com.srsali.srsali.enums.Permissao;
import br.com.srsali.srsali.models.InstituicaoDeEnsino;
import br.com.srsali.srsali.models.Usuario;
import br.com.srsali.srsali.repositories.InstituicaoDeEnsinoRepository;
import br.com.srsali.srsali.repositories.UsuarioRepository;
import br.com.srsali.srsali.utils.DaoUtils;

@RestController
@RequestMapping("/instituicoes")
public class InstituicaoDeEnsinoController {
	
	@Autowired InstituicaoDeEnsinoRepository instituicaoRepo;
	@Autowired UsuarioRepository usuarioRepo;

	@PostMapping
	public ResponseEntity<Void> incluir(@RequestBody Usuario criador) {
		final var instituicao = criador.getInstituicao();
		criador.setInstituicao(null);
		criador.getFuncoes().add(Funcao.ADMINISTRADOR);
		criador.getPermissoes().addAll(Set.of(Permissao.values()));
		
		instituicaoRepo.save(instituicao);
		criador.setInstituicao(instituicao);
		usuarioRepo.save(criador);
		
		return ResponseEntity.created(null).build();
	}
	
	@PutMapping("/{id}")
    public ResponseEntity<Void> alterar(@PathVariable int id, @RequestBody InstituicaoDeEnsino instituicao) {
	    var novaInstituicao = DaoUtils.find(instituicaoRepo, id, "Instituição não encontrada.");
	    BeanUtils.copyProperties(instituicao, novaInstituicao, "id");
	    instituicaoRepo.save(novaInstituicao);
	    
        return ResponseEntity.noContent().build();
    }
	
}