package br.com.srsali.srsali.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.srsali.srsali.models.Ferramenta;

@Repository
public interface FerramentaRepository extends CrudRepository<Ferramenta, Integer> {

}