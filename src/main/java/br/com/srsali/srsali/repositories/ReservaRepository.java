package br.com.srsali.srsali.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.srsali.srsali.models.Reserva;

@Repository
public interface ReservaRepository extends CrudRepository<Reserva, Integer> {

}
