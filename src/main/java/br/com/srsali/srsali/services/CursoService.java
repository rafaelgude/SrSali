package br.com.srsali.srsali.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.srsali.srsali.exceptions.DataIntegrityException;
import br.com.srsali.srsali.exceptions.ObjectNotFoundException;
import br.com.srsali.srsali.models.Curso;
import br.com.srsali.srsali.repositories.CursoRepository;

@Service
public class CursoService {
    
    @Autowired CursoRepository cursoRepo;
    @Autowired UsuarioService usuarioService;
    
    public List<Curso> findAll() {
        return cursoRepo.findAll();
    }
    
    public Curso find(int id) {
        return cursoRepo.findById(id).orElseThrow(() -> new ObjectNotFoundException(Curso.class.getName() + " não encontrado(a)."));
    }

    public void insert(Curso curso) {
        curso.setInstituicao(usuarioService.getAuthenticated().getInstituicao());
        cursoRepo.save(curso);
    }
    
    public void update(Curso curso) {
        cursoRepo.save(curso);
    }
    
    public void delete(int id) {
        try {
            cursoRepo.delete(find(id));
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(Curso.class.getName() + " possui vínculos com outros cadastros. Não é possível excluir.");
        }
    }
}
