package br.com.srsali.srsali.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.srsali.srsali.models.InstituicaoDeEnsino;
import br.com.srsali.srsali.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Transactional(readOnly = true)
    Usuario findByEmail(String email);
    
    @Transactional(readOnly = true)
    Usuario findByEmailAndIdNotIn(String email, int id);
    
    Page<Usuario> findAllByInstituicao(Pageable pageable, InstituicaoDeEnsino instituicao);
    
}
