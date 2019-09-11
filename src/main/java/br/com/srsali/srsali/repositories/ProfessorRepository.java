package br.com.srsali.srsali.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.srsali.srsali.models.Professor;

@Repository
public interface ProfessorRepository extends CrudRepository<Professor, Integer> {

}
