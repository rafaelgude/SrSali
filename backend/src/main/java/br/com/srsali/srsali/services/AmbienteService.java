package br.com.srsali.srsali.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.srsali.srsali.exceptions.DataIntegrityException;
import br.com.srsali.srsali.exceptions.ObjectNotFoundException;
import br.com.srsali.srsali.models.Ambiente;
import br.com.srsali.srsali.repositories.AmbienteRepository;

@Service
public class AmbienteService {
    
    @Autowired AmbienteRepository ambienteRepo;
    @Autowired UsuarioService usuarioService;
    
    public Page<Ambiente> findAll(int page, int linesPerPage, String orderBy, String direction) {
        return ambienteRepo.findAllByInstituicao(PageRequest.of(page, linesPerPage, Direction.valueOf(direction.toUpperCase()), orderBy), usuarioService.getAuthenticated().getInstituicao());
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
