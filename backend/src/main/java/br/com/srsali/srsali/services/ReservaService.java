package br.com.srsali.srsali.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.srsali.srsali.enums.TipoAmbiente;
import br.com.srsali.srsali.exceptions.DataIntegrityException;
import br.com.srsali.srsali.exceptions.ObjectNotFoundException;
import br.com.srsali.srsali.models.Reserva;
import br.com.srsali.srsali.repositories.ReservaRepository;

@Service
public class ReservaService {
    @Autowired ReservaRepository reservaRepo;
    @Autowired UsuarioService usuarioService;
    
    public Page<Reserva> findAll(int page, int linesPerPage, String orderBy, String direction) {
        return reservaRepo.findAllByInstituicaoOrderByDataDesc(PageRequest.of(page, linesPerPage, Direction.valueOf(direction.toUpperCase()), orderBy), usuarioService.getAuthenticated().getInstituicao());
    }
    
    public Page<Reserva> findAll(int page, int linesPerPage, String orderBy, String direction, TipoAmbiente tipoAmbiente, List<Integer> ambientes, List<Integer> horarios) {
        return reservaRepo.findAllByInstituicao(PageRequest.of(page, linesPerPage, Direction.valueOf(direction.toUpperCase()), orderBy), usuarioService.getAuthenticated().getInstituicao(), tipoAmbiente, ambientes, horarios);
    }
    
    public Reserva find(int id) {
        return reservaRepo.findById(id).orElseThrow(() -> new ObjectNotFoundException(Reserva.class.getName() + " não encontrado(a)."));
    }

    public void insert(Reserva reserva) {
        reserva.setInstituicao(usuarioService.getAuthenticated().getInstituicao());
        
        if (!reservaRepo.findByInstituicaoAndDataAndAmbienteAndHorario(reserva.getInstituicao(), reserva.getData(), reserva.getAmbiente(), reserva.getHorario()).isEmpty())
            new DataIntegrityException("Já existe uma reserva para este Ambiente e Horário nesta Data.");
        
        reservaRepo.save(reserva);
    }
    
    public void update(Reserva reserva) {
        reservaRepo.save(reserva);
    }
    
    public void delete(int id) {
        try {
            reservaRepo.delete(find(id));
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(Reserva.class.getName() + " possui vínculos com outros cadastros. Não é possível excluir.");
        }
    }
    
}
