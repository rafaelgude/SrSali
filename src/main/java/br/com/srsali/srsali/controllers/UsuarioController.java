package br.com.srsali.srsali.controllers;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.srsali.srsali.exceptions.DataIntegrityException;
import br.com.srsali.srsali.models.Usuario;
import br.com.srsali.srsali.repositories.InstituicaoDeEnsinoRepository;
import br.com.srsali.srsali.repositories.UsuarioRepository;
import br.com.srsali.srsali.utils.DaoUtils;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
    @Autowired UsuarioRepository usuarioRepo;
	@Autowired InstituicaoDeEnsinoRepository instituicaoRepo;
	@Autowired BCryptPasswordEncoder pe;
	
	@GetMapping
	public ResponseEntity<Iterable<Usuario>> listar() {
	    return ResponseEntity.ok().body(usuarioRepo.findAll());
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<Usuario> buscar(@PathVariable int id) {
        return ResponseEntity.ok().body(DaoUtils.find(usuarioRepo, id, "Usuário não encontrado."));
    }

	@PostMapping
	public ResponseEntity<Void> incluir(@RequestBody List<Usuario> usuarios) {
	    usuarios.forEach(usuario -> {
	        usuario.setInstituicao(DaoUtils.find(instituicaoRepo, 1, "Instituição não encontrada."));
	        usuario.setSenha(pe.encode(usuario.getSenha()));
	        usuarioRepo.save(usuario);
	    });
		
		return ResponseEntity.created(null).build();
	}
	
	@PutMapping("/{id}")
    public ResponseEntity<Void> alterar(@PathVariable int id, @RequestBody Usuario usuario) {
	    var novoUsuario = DaoUtils.find(usuarioRepo, id, "Usuário não encontrado.");
	    BeanUtils.copyProperties(usuario, novoUsuario, "id", "instituicao");
	    usuarioRepo.save(novoUsuario);
	    
        return ResponseEntity.noContent().build();
    }
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable int id) {
	    try {
	        usuarioRepo.delete(DaoUtils.find(usuarioRepo, id, "Usuário não encontrado."));
	    } catch (DataIntegrityViolationException e) {
	        throw new DataIntegrityException("Não é possível excluir um Usuário que possui vínculo com outros cadastros.");
	    }
	    
	    return ResponseEntity.noContent().build();
	}
}