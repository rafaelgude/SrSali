package br.com.srsali.srsali.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.srsali.srsali.models.Ambiente;

@Repository
public interface AmbienteRepository extends CrudRepository<Ambiente, Integer> {

}
