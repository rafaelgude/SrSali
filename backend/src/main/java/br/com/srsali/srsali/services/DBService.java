package br.com.srsali.srsali.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
    
    private static final List<String> NOMES_PROFESSORES = List.of("Erlon", "Susilea", "Saulo", "Ricardo", "Denis", "Vinicius", "Marcelo", "Alessandro", "Luciana");

    public void instantiateDatabase() {
        var uvv = instituicaoRepo.save(new InstituicaoDeEnsino("UVV"));
        
        // Usuários
        var usuarioRafael = new Usuario("Rafael", "rafael@gude.com", pe.encode("112233"), "1234-5678", uvv); 
        var usuarioErlon = new Usuario("Erlon", "erlon@uvv.br", pe.encode("123456"), "99999-8888", uvv); 
        usuarioErlon.getFuncoes().add(Funcao.ADMIN);
        usuarioErlon.getPermissoes().addAll(Set.of(Permissao.values()));
        usuarioRepo.saveAll(List.of(usuarioErlon, usuarioRafael));
        
        // Ferramentas
        var ferrComputador = new Ferramenta("Computador", uvv);
        var ferrQuadro = new Ferramenta("Quadro", uvv);
        var ferrCarteira = new Ferramenta("Carteira Escolar", uvv);
        ferramentaRepo.saveAll(List.of(ferrComputador, ferrQuadro, ferrCarteira));
        
        // Laboratórios
        final var laboratorios = new ArrayList<Ambiente>();
        for (int i = 1; i <= 10; i++) {
            var lab = new Ambiente("Laboratório " + i, getAmbienteLength(), uvv, TipoAmbiente.LABORATORIO_INFORMATICA);
            lab.getFerramentas().addAll(List.of(new AmbienteFerramenta(ferrComputador, lab, lab.getCapacidadeAlunos()), new AmbienteFerramenta(ferrQuadro, lab, 1)));
            laboratorios.add(ambienteRepo.save(lab));
        }
        
        // Salas
        final var salas = new ArrayList<Ambiente>();
        for (int i = 1; i <= 30; i++) {
            var sala = new Ambiente("Sala " + i, getAmbienteLength(), uvv, TipoAmbiente.SALA_AULA);
            sala.getFerramentas().addAll(List.of(new AmbienteFerramenta(ferrCarteira, sala, sala.getCapacidadeAlunos()), new AmbienteFerramenta(ferrQuadro, sala, 1))); 
            salas.add(ambienteRepo.save(sala));
        }
        
        // Professores
        final var professores = new ArrayList<Professor>();
        for (var nome : NOMES_PROFESSORES)
            professores.add(professorRepo.save(new Professor(nome.toLowerCase() + "@uvv.br", nome, "Erlon".equals(nome) ? usuarioErlon : null, uvv)));
        
        // Horários
        var h07 = new Horario("1º Horário", Turno.MATUTINO, LocalTime.of(07, 10), LocalTime.of(8, 50), uvv);
        var h09 = new Horario("2º Horário", Turno.MATUTINO, LocalTime.of(9, 05), LocalTime.of(11, 45), uvv);
        var h19 = new Horario("1º Horário", Turno.NOTURNO, LocalTime.of(19, 10), LocalTime.of(20, 50), uvv);
        var h21 = new Horario("2º Horário", Turno.NOTURNO, LocalTime.of(21, 05), LocalTime.of(22, 45), uvv);
        horarioRepo.saveAll(List.of(h07, h09, h19, h21));
        
        // Disciplinas
        var discProg1 = new Disciplina("Programação 1", uvv, Set.of(ferrQuadro, ferrComputador));
        var discProg2 = new Disciplina("Programação 2", uvv, Set.of(ferrQuadro, ferrComputador));
        var discGestaoProjetos = new Disciplina("Gestão de Projetos", uvv, Set.of(ferrQuadro, ferrComputador));
        var discBancoDados = new Disciplina("Banco de Dados", uvv, Set.of(ferrQuadro, ferrComputador));
        var discCalculo = new Disciplina("Cálculo 1", uvv, Set.of(ferrQuadro, ferrCarteira));
        var discCompiladores = new Disciplina("Compiladores", uvv, Set.of(ferrQuadro, ferrCarteira));
        disciplinaRepo.saveAll(List.of(discProg1, discProg2, discGestaoProjetos, discBancoDados, discCalculo, discCompiladores));
        
        // Cursos
        var curSistemasInformacao = new Curso("Sistemas de Informação", uvv, Set.of(discProg1, discProg2, discGestaoProjetos, discBancoDados, discCalculo));
        var curCienciasComputacao = new Curso("Ciências da Computação", uvv, Set.of(discProg1, discProg2, discBancoDados, discCalculo, discCompiladores));
        var curAdministracao = new Curso("Administração", uvv, Set.of(discGestaoProjetos));
        var curEngenhariaMecanica = new Curso("Engenharia Mecânica", uvv, Set.of(discProg1, discCalculo));
        cursoRepo.saveAll(List.of(curSistemasInformacao, curCienciasComputacao, curAdministracao, curEngenhariaMecanica));
        
        // Turmas
        var si8n = new Turma("SI8N", curSistemasInformacao, 7);
        var si6n = new Turma("SI6N", curSistemasInformacao, 14);
        var si4n = new Turma("SI4N", curSistemasInformacao, 22);
        var si2n = new Turma("SI2N", curSistemasInformacao, 35);
        var cc8m = new Turma("CC8M", curCienciasComputacao, 11);
        var cc6m = new Turma("CC6M", curCienciasComputacao, 17);
        var cc4m = new Turma("CC4M", curCienciasComputacao, 20);
        var em8n = new Turma("EM8N", curEngenhariaMecanica, 18);
        turmaRepo.saveAll(List.of(si8n, si6n, si4n, si2n, cc8m, cc6m, cc4m, em8n));
        
        // NOTRUNO 19h
        reservaRepo.saveAll(List.of(new Reserva(Set.of(si8n), laboratorios.get(5), uvv, professores.get(1), discGestaoProjetos, h19, LocalDate.of(2019, 12, 05), false),
                                    new Reserva(Set.of(si6n, cc6m), laboratorios.get(4), uvv, professores.get(6), discBancoDados, h19, LocalDate.of(2019, 12, 05), false),
                                    new Reserva(Set.of(si4n), laboratorios.get(3), uvv, professores.get(3), discProg2, h19, LocalDate.of(2019, 12, 05), false),
                                    new Reserva(Set.of(si2n), laboratorios.get(2), uvv, professores.get(7), discProg1, h19, LocalDate.of(2019, 12, 05), false),
                                    new Reserva(Set.of(em8n), salas.get(27), uvv, professores.get(0), discCalculo, h19, LocalDate.of(2019, 12, 05), false)));
        
        // NOTRUNO 21h
        reservaRepo.saveAll(List.of(new Reserva(Set.of(si8n), laboratorios.get(5), uvv, professores.get(1), discGestaoProjetos, h19, LocalDate.of(2019, 12, 05), false),
                                    new Reserva(Set.of(si6n), laboratorios.get(4), uvv, professores.get(6), discBancoDados, h19, LocalDate.of(2019, 12, 05), false),
                                    new Reserva(Set.of(si4n), laboratorios.get(3), uvv, professores.get(3), discProg2, h19, LocalDate.of(2019, 12, 05), false),
                                    new Reserva(Set.of(si2n), laboratorios.get(2), uvv, professores.get(7), discProg1, h19, LocalDate.of(2019, 12, 05), false),
                                    new Reserva(Set.of(em8n), salas.get(27), uvv, professores.get(4), discProg1, h19, LocalDate.of(2019, 12, 05), false)));
        
        // MATUTINO 07h
        reservaRepo.saveAll(List.of(new Reserva(Set.of(cc8m), laboratorios.get(1), uvv, professores.get(2), discCompiladores, h07, LocalDate.of(2019, 12, 05), false),
                                    new Reserva(Set.of(cc6m), laboratorios.get(2), uvv, professores.get(6), discBancoDados, h07, LocalDate.of(2019, 12, 05), false),
                                    new Reserva(Set.of(cc4m), salas.get(0), uvv, professores.get(8), discCalculo, h07, LocalDate.of(2019, 12, 05), false)));
        
        // MATUTINO 09h
        reservaRepo.saveAll(List.of(new Reserva(Set.of(cc8m), laboratorios.get(1), uvv, professores.get(2), discCompiladores, h09, LocalDate.of(2019, 12, 05), false),
                                    new Reserva(Set.of(cc6m), laboratorios.get(2), uvv, professores.get(3), discProg2, h09, LocalDate.of(2019, 12, 05), false),
                                    new Reserva(Set.of(cc4m), laboratorios.get(3), uvv, professores.get(7), discProg1, h09, LocalDate.of(2019, 12, 05), false)));
        
    }
    
    private int getAmbienteLength() {
        return ((new Random().nextInt(5)) * 5) + 20;
    }
    
}
