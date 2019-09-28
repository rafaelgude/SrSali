package br.com.srsali.srsali.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.srsali.srsali.models.Disciplina;
import br.com.srsali.srsali.models.InstituicaoDeEnsino;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Integer> {

    Page<Disciplina> findAllByInstituicao(Pageable pageable, InstituicaoDeEnsino instituicao);
    
}
