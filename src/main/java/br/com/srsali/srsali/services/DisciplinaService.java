package br.com.srsali.srsali.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.srsali.srsali.exceptions.DataIntegrityException;
import br.com.srsali.srsali.exceptions.ObjectNotFoundException;
import br.com.srsali.srsali.models.Disciplina;
import br.com.srsali.srsali.repositories.DisciplinaRepository;

@Service
public class DisciplinaService {
    
    @Autowired DisciplinaRepository disciplinaRepo;
    @Autowired UsuarioService usuarioService;
    
    public List<Disciplina> findAll() {
        return disciplinaRepo.findAll();
    }
    
    public Disciplina find(int id) {
        return disciplinaRepo.findById(id).orElseThrow(() -> new ObjectNotFoundException(Disciplina.class.getName() + " não encontrado(a)."));
    }

    public void insert(Disciplina disciplina) {
        disciplina.setInstituicao(usuarioService.getAuthenticated().getInstituicao());
        disciplinaRepo.save(disciplina);
    }
    
    public void update(Disciplina disciplina) {
        disciplinaRepo.save(disciplina);
    }
    
    public void delete(int id) {
        try {
            disciplinaRepo.delete(find(id));
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(Disciplina.class.getName() + " possui vínculos com outros cadastros. Não é possível excluir.");
        }
    }
}
