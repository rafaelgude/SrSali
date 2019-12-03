package br.com.srsali.srsali.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.srsali.srsali.enums.TipoAmbiente;
import br.com.srsali.srsali.models.InstituicaoDeEnsino;
import br.com.srsali.srsali.models.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    @Query(value = "select res "
                 + "from Reserva res inner join fetch res.ambiente amb inner join fetch res.horario hor "
                 + "where res.instituicao = :instituicao "
                 + "  and amb.tipoAmbiente = :tipoAmbiente "
                 + "  and (coalesce(:ambientes, null) is null or amb.id in (:ambientes)) "
                 + "  and (coalesce(:horarios, null) is null or hor.id in (:horarios)) "
                 + "order by length(amb.nome), amb.nome",
           countQuery = "select count(res) from Reserva where instituicao = :instituicao")
    Page<Reserva> findAllByInstituicao(Pageable pageable, @Param("instituicao") InstituicaoDeEnsino instituicao, @Param("tipoAmbiente") TipoAmbiente tipoAmbiente, 
                                       @Param("ambientes") List<Integer> ambientes,
                                       @Param("horarios") List<Integer> horarios);
    
}
