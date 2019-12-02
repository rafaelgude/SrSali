package br.com.srsali.srsali.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Reserva implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToMany
    @JoinTable(name = "reserva_turma", 
               joinColumns = @JoinColumn(name = "reserva_id"), 
               inverseJoinColumns = @JoinColumn(name = "turma_id"))
	private Set<Turma> turmas = new HashSet<>();

	@ManyToOne
    @JoinColumn(name = "ambiente_id")
	@NotNull(message = "Ambiente é obrigatório.")
	private Ambiente ambiente;

	@JsonIgnore
	@ManyToOne
    @JoinColumn(name = "instituicao_id")
	@NotNull(message = "Instituição é obrigatório.")
	private InstituicaoDeEnsino instituicao;

	@ManyToOne
    @JoinColumn(name = "horario_id")
	@NotNull(message = "Horário é obrigatório.")
	private Horario horario;

	@ManyToOne
    @JoinColumn(name = "professor_id")
	private Professor professor;

	@ManyToOne
    @JoinColumn(name = "disciplina_id")
	private Disciplina disciplina;

	@NotNull(message = "Data é obrigatório.")
	private LocalDate data;

	private boolean preReserva;

    public Reserva() {
    }

    public Reserva(Set<Turma> turmas, Ambiente ambiente, InstituicaoDeEnsino instituicao, Professor professor, 
                   Disciplina disciplina, Horario horario, LocalDate data, boolean preReserva) {
        super();
        this.turmas = turmas;
        this.ambiente = ambiente;
        this.instituicao = instituicao;
        this.professor = professor;
        this.disciplina = disciplina;
        this.horario = horario;
        this.data = data;
        this.preReserva = preReserva;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Turma> getTurmas() {
        return turmas;
    }

    public void setTurmas(Set<Turma> turmas) {
        this.turmas = turmas;
    }
    
    @JsonProperty
    public void setTurmas(int[] turmas) {
        for (var x : turmas)
            this.turmas.add(new Turma(x));
    }

    public Ambiente getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;
    }

    public InstituicaoDeEnsino getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(InstituicaoDeEnsino instituicao) {
        this.instituicao = instituicao;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public boolean isPreReserva() {
        return preReserva;
    }

    public void setPreReserva(boolean preReserva) {
        this.preReserva = preReserva;
    }

}
