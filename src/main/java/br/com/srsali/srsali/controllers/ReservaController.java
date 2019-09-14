package br.com.srsali.srsali.controllers;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.srsali.srsali.models.Reserva;
import br.com.srsali.srsali.services.ReservaService;

@RestController
@RequestMapping("/reservas")
public class ReservaController {
    
    @Autowired ReservaService reservaService;
    
    @GetMapping
    public ResponseEntity<Page<Reserva>> findAll(@RequestParam(value="page", defaultValue="0") int page, 
                                                 @RequestParam(value="linesPerPage", defaultValue="24") int linesPerPage, 
                                                 @RequestParam(value="orderBy", defaultValue="data") String orderBy, 
                                                 @RequestParam(value="direction", defaultValue="DESC") String direction) {
        return ResponseEntity.ok().body(reservaService.findAll(page, linesPerPage, orderBy, direction));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> find(@PathVariable int id) {
        return ResponseEntity.ok().body(reservaService.find(id));
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody List<Reserva> reservas) {
        reservas.forEach(reservaService::insert);
        return ResponseEntity.created(null).build();
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody Reserva reserva) {
        var newReserva = reservaService.find(id);
        BeanUtils.copyProperties(reserva, newReserva, "id", "instituicao");
        reservaService.update(newReserva);
        
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        reservaService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}
