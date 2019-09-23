package br.com.srsali.srsali.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.srsali.srsali.models.Ferramenta;

@Repository
public interface FerramentaRepository extends JpaRepository<Ferramenta, Integer> {

}
