package br.com.srsali.srsali.models;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Reserva implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private Turma turmas;

	private SalaLaboratorio salaLaboratorio;

	private InstituicaoDeEnsino instituicao;

	private int turno;

	private Horario horario;

	private Professor professor;

	private Disciplina disciplina;

	private LocalDate data;

	private boolean preReserva;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Turma getTurmas() {
		return turmas;
	}

	public void setTurmas(Turma turmas) {
		this.turmas = turmas;
	}

	public SalaLaboratorio getSalaLaboratorio() {
		return salaLaboratorio;
	}

	public void setSalaLaboratorio(SalaLaboratorio salaLaboratorio) {
		this.salaLaboratorio = salaLaboratorio;
	}

	public InstituicaoDeEnsino getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(InstituicaoDeEnsino instituicao) {
		this.instituicao = instituicao;
	}

	public int getTurno() {
		return turno;
	}

	public void setTurno(int turno) {
		this.turno = turno;
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
