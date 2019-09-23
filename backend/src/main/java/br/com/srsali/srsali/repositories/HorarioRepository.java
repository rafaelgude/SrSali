package br.com.srsali.srsali.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.srsali.srsali.models.Horario;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Integer> {

}
