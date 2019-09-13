package br.com.srsali.srsali.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.srsali.srsali.enums.Funcao;
import br.com.srsali.srsali.enums.Permissao;
import br.com.srsali.srsali.enums.TipoAmbiente;
import br.com.srsali.srsali.models.Ambiente;
import br.com.srsali.srsali.models.AmbienteFerramenta;
import br.com.srsali.srsali.models.Curso;
import br.com.srsali.srsali.models.Disciplina;
import br.com.srsali.srsali.models.Ferramenta;
import br.com.srsali.srsali.models.InstituicaoDeEnsino;
import br.com.srsali.srsali.models.Professor;
import br.com.srsali.srsali.models.Usuario;
import br.com.srsali.srsali.repositories.AmbienteRepository;
import br.com.srsali.srsali.repositories.CursoRepository;
import br.com.srsali.srsali.repositories.DisciplinaRepository;
import br.com.srsali.srsali.repositories.FerramentaRepository;
import br.com.srsali.srsali.repositories.InstituicaoDeEnsinoRepository;
import br.com.srsali.srsali.repositories.ProfessorRepository;
import br.com.srsali.srsali.repositories.UsuarioRepository;

@Service
public class DBService {
    
    @Autowired private InstituicaoDeEnsinoRepository instituicaoRepo;
    @Autowired private UsuarioRepository usuarioRepo;
    @Autowired private ProfessorRepository professorRepo;
    @Autowired private FerramentaRepository ferramentaRepo;
    @Autowired private AmbienteRepository ambienteRepo;
    @Autowired private DisciplinaRepository disciplinaRepo;
    @Autowired private CursoRepository cursoRepo;
    @Autowired BCryptPasswordEncoder pe;

    public void instantiateTestDatabase() {
        var uvv = instituicaoRepo.save(new InstituicaoDeEnsino("UVV", true));
        
        var rafael = new Usuario("Rafael", "rafael@gude.com", pe.encode("147258369"), "1234-5678", uvv); 
        var erlon = new Usuario("Erlon", "erlon@uvv.br", pe.encode("123456"), "99999-8888", uvv); 
        erlon.getFuncoes().add(Funcao.ADMINISTRADOR);
        erlon.getPermissoes().addAll(Set.of(Permissao.values()));
        usuarioRepo.saveAll(List.of(erlon, rafael));
        
        professorRepo.saveAll(List.of(new Professor("erlon@uvv.br", "Erlon", erlon, uvv, true), 
                                      new Professor("susilea@uvv.br", "Susiléa", null, uvv, true)));
        
        var computador = new Ferramenta("Computador", uvv, true);
        var quadro = new Ferramenta("Quadro", uvv, true);
        var mesa = new Ferramenta("Mesa", uvv, false);
        
        ferramentaRepo.saveAll(List.of(computador, quadro, mesa));
        
        var lab1 = new Ambiente("Laboratório 1", 30, uvv, TipoAmbiente.LABORATORIO_INFORMATICA, true);
        lab1.getFerramentas().addAll(List.of(new AmbienteFerramenta(computador, lab1, 30),
                                             new AmbienteFerramenta(quadro, lab1, 1)));
        
        var sala1 = new Ambiente("Sala 1", 25, uvv, TipoAmbiente.SALA_AULA, true);
        sala1.getFerramentas().addAll(List.of(new AmbienteFerramenta(quadro, sala1, 1),
                                              new AmbienteFerramenta(mesa, sala1, 5))); 
        
        ambienteRepo.saveAll(List.of(lab1, sala1));
        
        var labSoRedes = new Disciplina("Laboratório de Redes de S.O.", uvv, true, Set.of(computador, quadro));
        var calculo = new Disciplina("Cálculo 1", uvv, true, Set.of(quadro, mesa));
        disciplinaRepo.saveAll(List.of(labSoRedes, calculo));
        
        var sistemasInformacao = new Curso("Sistemas de Informação", uvv, true, Set.of(labSoRedes, calculo));
        cursoRepo.save(sistemasInformacao);
        
    }
    
}
