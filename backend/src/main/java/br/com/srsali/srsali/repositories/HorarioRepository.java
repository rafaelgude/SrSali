package br.com.srsali.srsali.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.srsali.srsali.models.Horario;
import br.com.srsali.srsali.models.InstituicaoDeEnsino;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Integer> {

    Page<Horario> findAllByInstituicao(Pageable pageable, InstituicaoDeEnsino instituicao);
    
}
