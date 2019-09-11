package br.com.srsali.srsali.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.srsali.srsali.models.Curso;

@Repository
public interface CursoRepository extends CrudRepository<Curso, Integer> {

}
