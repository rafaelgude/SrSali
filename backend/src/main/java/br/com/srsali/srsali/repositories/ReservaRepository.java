package br.com.srsali.srsali.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.srsali.srsali.models.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

}
