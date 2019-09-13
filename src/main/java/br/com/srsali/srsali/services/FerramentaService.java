package br.com.srsali.srsali.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.srsali.srsali.exceptions.DataIntegrityException;
import br.com.srsali.srsali.exceptions.ObjectNotFoundException;
import br.com.srsali.srsali.models.Ferramenta;
import br.com.srsali.srsali.repositories.FerramentaRepository;

@Service
public class FerramentaService {
    
    @Autowired FerramentaRepository ferramentaRepo;
    @Autowired UsuarioService usuarioService;
    
    public List<Ferramenta> findAll() {
        return ferramentaRepo.findAll();
    }
    
    public Ferramenta find(int id) {
        return ferramentaRepo.findById(id).orElseThrow(() -> new ObjectNotFoundException(Ferramenta.class.getName() + " não encontrado(a)."));
    }

    public void insert(Ferramenta ferramenta) {
        ferramenta.setInstituicao(usuarioService.getAuthenticated().getInstituicao());
        ferramentaRepo.save(ferramenta);
    }
    
    public void update(Ferramenta ferramenta) {
        ferramentaRepo.save(ferramenta);
    }
    
    public void delete(int id) {
        try {
            ferramentaRepo.delete(find(id));
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(Ferramenta.class.getName() + " possui vínculos com outros cadastros. Não é possível excluir.");
        }
    }
}
