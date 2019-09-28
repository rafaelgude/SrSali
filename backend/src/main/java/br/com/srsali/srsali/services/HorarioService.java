package br.com.srsali.srsali.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.srsali.srsali.exceptions.DataIntegrityException;
import br.com.srsali.srsali.exceptions.ObjectNotFoundException;
import br.com.srsali.srsali.models.Horario;
import br.com.srsali.srsali.repositories.HorarioRepository;

@Service
public class HorarioService {
    
    @Autowired HorarioRepository horarioRepo;
    @Autowired UsuarioService usuarioService;
    
    public Page<Horario> findAll(int page, int linesPerPage, String orderBy, String direction) {
        return horarioRepo.findAllByInstituicao(PageRequest.of(page, linesPerPage, Direction.valueOf(direction.toUpperCase()), orderBy.split(",")), usuarioService.getAuthenticated().getInstituicao());
    }
    
    public Horario find(int id) {
        return horarioRepo.findById(id).orElseThrow(() -> new ObjectNotFoundException(Horario.class.getName() + " não encontrado(a)."));
    }

    public void insert(Horario horario) {
        horario.setInstituicao(usuarioService.getAuthenticated().getInstituicao());
        horarioRepo.save(horario);
    }
    
    public void update(Horario horario) {
        horarioRepo.save(horario);
    }
    
    public void delete(int id) {
        try {
            horarioRepo.delete(find(id));
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(Horario.class.getName() + " possui vínculos com outros cadastros. Não é possível excluir.");
        }
    }
}
