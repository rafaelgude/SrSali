package br.com.srsali.srsali.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.srsali.srsali.enums.Funcao;
import br.com.srsali.srsali.models.Ferramenta;
import br.com.srsali.srsali.models.InstituicaoDeEnsino;
import br.com.srsali.srsali.models.Professor;
import br.com.srsali.srsali.models.Usuario;
import br.com.srsali.srsali.repositories.FerramentaRepository;
import br.com.srsali.srsali.repositories.InstituicaoDeEnsinoRepository;
import br.com.srsali.srsali.repositories.ProfessorRepository;
import br.com.srsali.srsali.repositories.UsuarioRepository;

@Service
public class DBService {
    
    @Autowired
    private InstituicaoDeEnsinoRepository instituicaoRepo;
    
    @Autowired
    private UsuarioRepository usuarioRepo;
    
    @Autowired
    private ProfessorRepository professorRepo;
    
    @Autowired
    private FerramentaRepository ferramentaRepo;

    public void instantiateTestDatabase() {
        var uvv = instituicaoRepo.save(new InstituicaoDeEnsino("UVV", true));
        var erlon = new Usuario("Erlon", "erlon@uvv.br", "123456", "99999-8888", uvv); 
        erlon.getFuncoes().add(Funcao.ADMINISTRADOR);
        usuarioRepo.save(erlon);
        
        professorRepo.saveAll(List.of(new Professor("erlon@uvv.br", "Erlon", erlon, uvv, true), 
                                      new Professor("susilea@uvv.br", "Susiléa", null, uvv, true)));
        
        ferramentaRepo.saveAll(List.of(new Ferramenta("Computador", uvv, true),
                                       new Ferramenta("Quadro", uvv, true),
                                       new Ferramenta("Mesa", uvv, false)));
    }
    
}
