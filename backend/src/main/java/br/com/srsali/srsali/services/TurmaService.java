package br.com.srsali.srsali.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.srsali.srsali.exceptions.DataIntegrityException;
import br.com.srsali.srsali.exceptions.ObjectNotFoundException;
import br.com.srsali.srsali.models.Turma;
import br.com.srsali.srsali.repositories.TurmaRepository;

@Service
public class TurmaService {
    
    @Autowired TurmaRepository turmaRepo;
    
    public Page<Turma> findAll(int page, int linesPerPage, String orderBy, String direction) {
        return turmaRepo.findAll(PageRequest.of(page, linesPerPage, Direction.valueOf(direction.toUpperCase()), orderBy));
    }
    
    public Turma find(int id) {
        return turmaRepo.findById(id).orElseThrow(() -> new ObjectNotFoundException(Turma.class.getName() + " não encontrado(a)."));
    }

    public void insert(Turma turma) {
        turmaRepo.save(turma);
    }
    
    public void update(Turma turma) {
        turmaRepo.save(turma);
    }
    
    public void delete(int id) {
        try {
            turmaRepo.delete(find(id));
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(Turma.class.getName() + " possui vínculos com outros cadastros. Não é possível excluir.");
        }
    }
}
