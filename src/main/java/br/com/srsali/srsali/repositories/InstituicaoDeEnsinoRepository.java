package br.com.srsali.srsali.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.srsali.srsali.models.InstituicaoDeEnsino;

@Repository
public interface InstituicaoDeEnsinoRepository extends CrudRepository<InstituicaoDeEnsino, Integer> {

}
