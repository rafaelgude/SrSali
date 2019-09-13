package br.com.srsali.srsali.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.srsali.srsali.exceptions.DataIntegrityException;
import br.com.srsali.srsali.exceptions.ObjectNotFoundException;
import br.com.srsali.srsali.models.Horario;
import br.com.srsali.srsali.repositories.HorarioRepository;

@Service
public class HorarioService {
    
    @Autowired HorarioRepository horarioRepo;
    @Autowired UsuarioService usuarioService;
    
    public List<Horario> findAll() {
        return horarioRepo.findAll();
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
