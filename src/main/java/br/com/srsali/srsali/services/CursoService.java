package br.com.srsali.srsali.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.srsali.srsali.exceptions.DataIntegrityException;
import br.com.srsali.srsali.exceptions.ObjectNotFoundException;
import br.com.srsali.srsali.models.Curso;
import br.com.srsali.srsali.repositories.CursoRepository;

@Service
public class CursoService {
    
    @Autowired CursoRepository cursoRepo;
    @Autowired UsuarioService usuarioService;
    
    public Page<Curso> findAll(int page, int linesPerPage, String orderBy, String direction) {
        return cursoRepo.findAll(PageRequest.of(page, linesPerPage, Direction.valueOf(direction.toUpperCase()), orderBy));
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
