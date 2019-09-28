package br.com.srsali.srsali.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.srsali.srsali.exceptions.DataIntegrityException;
import br.com.srsali.srsali.exceptions.ObjectNotFoundException;
import br.com.srsali.srsali.models.Professor;
import br.com.srsali.srsali.repositories.ProfessorRepository;

@Service
public class ProfessorService {
    
    @Autowired ProfessorRepository professorRepo;
    @Autowired UsuarioService usuarioService;
    
    public Page<Professor> findAll(int page, int linesPerPage, String orderBy, String direction) {
        return professorRepo.findAllByInstituicao(PageRequest.of(page, linesPerPage, Direction.valueOf(direction.toUpperCase()), orderBy), usuarioService.getAuthenticated().getInstituicao());
    }
    
    public Professor find(int id) {
        return professorRepo.findById(id).orElseThrow(() -> new ObjectNotFoundException(Professor.class.getName() + " não encontrado(a)."));
    }

    public void insert(Professor professor) {
        professor.setUsuario(professor.getUsuario() == null ? null : usuarioService.find(professor.getUsuario().getId()));
        professor.setInstituicao(usuarioService.getAuthenticated().getInstituicao());
        professorRepo.save(professor);
    }
    
    public void update(Professor professor) {
        professorRepo.save(professor);
    }
    
    public void delete(int id) {
        try {
            professorRepo.delete(find(id));
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(Professor.class.getName() + " possui vínculos com outros cadastros. Não é possível excluir.");
        }
    }
}
