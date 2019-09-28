package br.com.srsali.srsali.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.srsali.srsali.models.InstituicaoDeEnsino;
import br.com.srsali.srsali.models.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    Page<Reserva> findAllByInstituicao(Pageable pageable, InstituicaoDeEnsino instituicao);
    
}
