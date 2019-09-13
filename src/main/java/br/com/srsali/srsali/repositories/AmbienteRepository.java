package br.com.srsali.srsali.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.srsali.srsali.models.Ambiente;

@Repository
public interface AmbienteRepository extends JpaRepository<Ambiente, Integer> {

}
