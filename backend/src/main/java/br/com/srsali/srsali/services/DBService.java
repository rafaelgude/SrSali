package br.com.srsali.srsali.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.srsali.srsali.enums.Funcao;
import br.com.srsali.srsali.enums.Permissao;
import br.com.srsali.srsali.enums.TipoAmbiente;
import br.com.srsali.srsali.enums.Turno;
import br.com.srsali.srsali.models.Ambiente;
import br.com.srsali.srsali.models.AmbienteFerramenta;
import br.com.srsali.srsali.models.Curso;
import br.com.srsali.srsali.models.Disciplina;
import br.com.srsali.srsali.models.Ferramenta;
import br.com.srsali.srsali.models.Horario;
import br.com.srsali.srsali.models.InstituicaoDeEnsino;
import br.com.srsali.srsali.models.Professor;
import br.com.srsali.srsali.models.Reserva;
import br.com.srsali.srsali.models.Turma;
import br.com.srsali.srsali.models.Usuario;
import br.com.srsali.srsali.repositories.AmbienteRepository;
import br.com.srsali.srsali.repositories.CursoRepository;
import br.com.srsali.srsali.repositories.DisciplinaRepository;
import br.com.srsali.srsali.repositories.FerramentaRepository;
import br.com.srsali.srsali.repositories.HorarioRepository;
import br.com.srsali.srsali.repositories.InstituicaoDeEnsinoRepository;
import br.com.srsali.srsali.repositories.ProfessorRepository;
import br.com.srsali.srsali.repositories.ReservaRepository;
import br.com.srsali.srsali.repositories.TurmaRepository;
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
    @Autowired private ReservaRepository reservaRepo;
    @Autowired private HorarioRepository horarioRepo;
    @Autowired private TurmaRepository turmaRepo;
    @Autowired BCryptPasswordEncoder pe;

    public void instantiateDatabase() {
        var uvv = instituicaoRepo.save(new InstituicaoDeEnsino("UVV", true));
        
        var rafael = new Usuario("Rafael", "rafael@gude.com", pe.encode("112233"), "1234-5678", uvv); 
        var erlon = new Usuario("Erlon", "erlon@uvv.br", pe.encode("123456"), "99999-8888", uvv); 
        erlon.getFuncoes().add(Funcao.ADMIN);
        erlon.getPermissoes().addAll(Set.of(Permissao.values()));
        usuarioRepo.saveAll(List.of(erlon, rafael));
        
        var profErlon = new Professor("erlon@uvv.br", "Erlon", erlon, uvv, true);
        var profSusi = new Professor("susilea@uvv.br", "Susiléa", null, uvv, true);
        
        professorRepo.saveAll(List.of(profErlon, profSusi));
        
        var ferrComputador = new Ferramenta("Computador", uvv, true);
        var ferrQuadro = new Ferramenta("Quadro", uvv, true);
        var ferrMesa = new Ferramenta("Mesa", uvv, false);
        
        ferramentaRepo.saveAll(List.of(ferrComputador, ferrQuadro, ferrMesa));
        
        var ambLab1 = new Ambiente("Laboratório 1", 30, uvv, TipoAmbiente.LABORATORIO_INFORMATICA, true);
        ambLab1.getFerramentas().addAll(List.of(new AmbienteFerramenta(ferrComputador, ambLab1, 30),
                                             new AmbienteFerramenta(ferrQuadro, ambLab1, 1)));
        
        var ambSala1 = new Ambiente("Sala 1", 25, uvv, TipoAmbiente.SALA_AULA, true);
        ambSala1.getFerramentas().addAll(List.of(new AmbienteFerramenta(ferrQuadro, ambSala1, 1),
                                              new AmbienteFerramenta(ferrMesa, ambSala1, 5))); 
        
        ambienteRepo.saveAll(List.of(ambLab1, ambSala1));
        
        var discLabSoRedes = new Disciplina("Laboratório de Redes de S.O.", uvv, true, Set.of(ferrComputador, ferrQuadro));
        var discCalculo = new Disciplina("Cálculo 1", uvv, true, Set.of(ferrQuadro, ferrMesa));
        disciplinaRepo.saveAll(List.of(discLabSoRedes, discCalculo));
        
        var curSistemasInformacao = new Curso("Sistemas de Informação", uvv, true, Set.of(discLabSoRedes, discCalculo));
        cursoRepo.save(curSistemasInformacao);
        
        var turma = new Turma("SI8N", curSistemasInformacao, 10, true);
        turmaRepo.saveAll(List.of(turma));
        
        var h07 = new Horario("Primeiro Horário", Turno.MATUTINO, LocalTime.of(07, 10), LocalTime.of(8, 50), uvv);
        var h09 = new Horario("Segundo Horário", Turno.MATUTINO, LocalTime.of(9, 05), LocalTime.of(11, 45), uvv);
        
        var h19 = new Horario("Primeiro Horário", Turno.NOTURNO, LocalTime.of(19, 10), LocalTime.of(20, 50), uvv);
        var h21 = new Horario("Segundo Horário", Turno.NOTURNO, LocalTime.of(21, 05), LocalTime.of(22, 45), uvv);
        
        horarioRepo.saveAll(List.of(h07, h09, h19, h21));
        
        reservaRepo.saveAll(List.of(new Reserva(Set.of(turma), ambLab1, uvv, h19, profSusi, discLabSoRedes, LocalDate.of(2019, 9, 27), false)));
        reservaRepo.saveAll(List.of(new Reserva(Set.of(turma), ambLab1, uvv, h19, profSusi, discLabSoRedes, LocalDate.of(2019, 9, 27), false)));
    }
    
}
