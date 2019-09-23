package br.com.srsali.srsali.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.srsali.srsali.enums.Funcao;
import br.com.srsali.srsali.enums.Permissao;
import br.com.srsali.srsali.exceptions.ObjectNotFoundException;
import br.com.srsali.srsali.models.InstituicaoDeEnsino;
import br.com.srsali.srsali.models.Usuario;
import br.com.srsali.srsali.repositories.InstituicaoDeEnsinoRepository;

@Service
public class InstituicaoDeEnsinoService {
    
    @Autowired InstituicaoDeEnsinoRepository instituicaoRepo;
    @Autowired UsuarioService usuarioService;
    
    public InstituicaoDeEnsino find(int id) {
        return instituicaoRepo.findById(id).orElseThrow(() -> new ObjectNotFoundException(InstituicaoDeEnsino.class.getName() + " não encontrado(a)."));
    }

    public void insert(Usuario criador) {
        var instituicao = instituicaoRepo.save(criador.getInstituicao());
        
        criador.setInstituicao(instituicao);
        criador.getFuncoes().add(Funcao.ADMIN);
        criador.getPermissoes().addAll(Set.of(Permissao.values()));
        usuarioService.insert(criador);
    }
    
    public void update(InstituicaoDeEnsino instituicao) {
        instituicaoRepo.save(instituicao);
    }
    
}
