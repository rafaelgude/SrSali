package br.com.srsali.srsali.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.srsali.srsali.enums.TipoAmbiente;
import br.com.srsali.srsali.models.Ambiente;
import br.com.srsali.srsali.models.InstituicaoDeEnsino;

@Repository
public interface AmbienteRepository extends JpaRepository<Ambiente, Integer> {
    
    @Query("SELECT amb FROM Ambiente amb WHERE amb.instituicao = :instituicao AND amb.tipoAmbiente = :tipoAmbiente ORDER BY LENGTH(nome), nome")
    Page<Ambiente> findAllByInstituicao(Pageable pageable, @Param("instituicao") InstituicaoDeEnsino instituicao, @Param("tipoAmbiente") TipoAmbiente tipoAmbiente);

}
