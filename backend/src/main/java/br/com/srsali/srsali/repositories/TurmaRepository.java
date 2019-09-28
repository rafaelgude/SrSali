package br.com.srsali.srsali.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.srsali.srsali.models.InstituicaoDeEnsino;
import br.com.srsali.srsali.models.Turma;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Integer> {

    Page<Turma> findAllByCursoInstituicao(Pageable pageable, InstituicaoDeEnsino instituicao);
    
}
