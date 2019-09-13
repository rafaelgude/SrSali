package br.com.srsali.srsali.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.srsali.srsali.models.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer> {

}
