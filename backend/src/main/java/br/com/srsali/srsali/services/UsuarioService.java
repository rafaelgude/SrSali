package br.com.srsali.srsali.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.srsali.srsali.enums.Funcao;
import br.com.srsali.srsali.exceptions.AuthorizationException;
import br.com.srsali.srsali.exceptions.DataIntegrityException;
import br.com.srsali.srsali.exceptions.ObjectNotFoundException;
import br.com.srsali.srsali.models.Usuario;
import br.com.srsali.srsali.repositories.UsuarioRepository;
import br.com.srsali.srsali.security.UsuarioJWT;

@Service
public class UsuarioService {
    
    @Autowired UsuarioRepository usuarioRepo;
    @Autowired BCryptPasswordEncoder pe;
    
    public Page<Usuario> findAll(int page, int linesPerPage, String orderBy, String direction) {
        return usuarioRepo.findAllByInstituicao(PageRequest.of(page, linesPerPage, Direction.valueOf(direction.toUpperCase()), orderBy), getAuthenticated().getInstituicao());
    }
    
    public Usuario find(int id) {
        var usuarioJwt = getUsuarioJWT();
        if (usuarioJwt == null || (!usuarioJwt.hasRole(Funcao.ADMIN) && id != usuarioJwt.getId()))
            throw new AuthorizationException("Acesso negado.");
        
        return usuarioRepo.findById(id).orElseThrow(() -> new ObjectNotFoundException(Usuario.class.getName() + " não encontrado(a)."));
    }
    
    public void insert(Usuario usuario) {
        if (usuarioRepo.findByEmail(usuario.getEmail()) != null)
            throw new DataIntegrityException("E-mail já existente.");
        
        usuario.setSenha(pe.encode(usuario.getSenha()));
        if (getAuthenticated() != null)
            usuario.setInstituicao(getAuthenticated().getInstituicao());
        
        usuarioRepo.save(usuario);
    }
    
    public void update(Usuario usuario) {
        if (usuarioRepo.findByEmailAndIdNotIn(usuario.getEmail(), usuario.getId()) != null)
            throw new DataIntegrityException("E-mail já existente.");
        
        usuarioRepo.save(usuario);
    }
    
    public void delete(int id) {
        try {
            usuarioRepo.delete(find(id));
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(Usuario.class.getName() + " possui vínculos com outros cadastros. Não é possível excluir.");
        }
    }
    
    public UsuarioJWT getUsuarioJWT() {
        try {
            return (UsuarioJWT) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }
    
    public Usuario getAuthenticated() {
        try {
            return find(getUsuarioJWT().getId());
        } catch (Exception e) {
            return null;
        }
    }
}
