package br.com.srsali.srsali.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.srsali.srsali.exceptions.DataIntegrityException;
import br.com.srsali.srsali.exceptions.ObjectNotFoundException;
import br.com.srsali.srsali.models.Ambiente;
import br.com.srsali.srsali.repositories.AmbienteRepository;

@Service
public class AmbienteService {
    
    @Autowired AmbienteRepository ambienteRepo;
    @Autowired UsuarioService usuarioService;
    
    public List<Ambiente> findAll() {
        return ambienteRepo.findAll();
    }
    
    public Ambiente find(int id) {
        return ambienteRepo.findById(id).orElseThrow(() -> new ObjectNotFoundException(Ambiente.class.getName() + " não encontrado(a)."));
    }

    public void insert(Ambiente ambiente) {
        ambiente.setInstituicao(usuarioService.getAuthenticated().getInstituicao());
        ambienteRepo.save(ambiente);
    }
    
    public void update(Ambiente ambiente) {
        ambienteRepo.save(ambiente);
    }
    
    public void delete(int id) {
        try {
            ambienteRepo.delete(find(id));
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(Ambiente.class.getName() + " possui vínculos com outros cadastros. Não é possível excluir.");
        }
    }
}
