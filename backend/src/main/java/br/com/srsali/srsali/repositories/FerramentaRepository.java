package br.com.srsali.srsali.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.srsali.srsali.models.Ferramenta;
import br.com.srsali.srsali.models.InstituicaoDeEnsino;

@Repository
public interface FerramentaRepository extends JpaRepository<Ferramenta, Integer> {

    Page<Ferramenta> findAllByInstituicao(Pageable pageable, InstituicaoDeEnsino instituicao);
    
}
