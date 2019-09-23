package br.com.srsali.srsali.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.srsali.srsali.models.InstituicaoDeEnsino;

@Repository
public interface InstituicaoDeEnsinoRepository extends JpaRepository<InstituicaoDeEnsino, Integer> {

}
