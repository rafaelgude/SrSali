package br.com.srsali.srsali.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.srsali.srsali.models.Turma;

@Repository
public interface TurmaRepository extends CrudRepository<Turma, Long> {

}
