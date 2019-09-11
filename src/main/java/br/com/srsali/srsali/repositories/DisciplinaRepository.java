package br.com.srsali.srsali.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.srsali.srsali.models.Disciplina;

@Repository
public interface DisciplinaRepository extends CrudRepository<Disciplina, Integer> {

}
