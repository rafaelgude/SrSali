package br.com.srsali.srsali.controllers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

import br.com.srsali.srsali.enums.TipoAmbiente;
import br.com.srsali.srsali.models.Reserva;
import br.com.srsali.srsali.services.AmbienteService;
import br.com.srsali.srsali.services.ReservaService;

@RestController
@RequestMapping("/reservas")
public class ReservaController {
    
    @Autowired ReservaService reservaService;
    @Autowired AmbienteService ambienteService;
    
    @GetMapping
    public ResponseEntity<Page<Reserva>> findAll(@RequestParam(value="page", defaultValue="0") int page, 
                                                 @RequestParam(value="linesPerPage", defaultValue="24") int linesPerPage, 
                                                 @RequestParam(value="orderBy", defaultValue="data") String orderBy, 
                                                 @RequestParam(value="direction", defaultValue="DESC") String direction,
                                                 @RequestParam(value="tipoAmbiente", required = false) String tipoAmbiente,
                                                 @RequestParam(value="ambientes", required = false) String ambientesIds,
                                                 @RequestParam(value="horarios", required = false) String horariosIds,
                                                 @RequestParam(value="turnos", required = false) String turnos) {
        if (tipoAmbiente == null || tipoAmbiente.isBlank())
            return ResponseEntity.ok().body(reservaService.findAll(page, linesPerPage, orderBy, direction));
        else {
            List<Integer> ambientes = ambientesIds != null && !ambientesIds.isEmpty() ? Stream.of(ambientesIds.split(",")).map(Integer::parseInt).collect(Collectors.toList()) : null;
            List<Integer> horarios = horariosIds != null && !horariosIds.isEmpty() ? Stream.of(horariosIds.split(",")).map(Integer::parseInt).collect(Collectors.toList()) : null;
            return ResponseEntity.ok().body(reservaService.findAll(page, linesPerPage, orderBy, direction, TipoAmbiente.toEnum(tipoAmbiente), ambientes, horarios));
        }
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
