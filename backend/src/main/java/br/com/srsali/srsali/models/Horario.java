package br.com.srsali.srsali.models;

import java.io.Serializable;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.srsali.srsali.enums.Turno;

@Entity
public class Horario implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull(message = "Nome é obrigatório.")
	private String nome;

	@Enumerated
	private Turno turno;

	@NotNull(message = "Horário de Início é obrigatório.")
	private LocalTime horaInicio;

	@NotNull(message = "Horário de Fim é obrigatório.")
	private LocalTime horaFim;

	@JsonIgnore
	@ManyToOne
    @JoinColumn(name = "instituicao_id")
	@NotNull(message = "Instituição é obrigatório.")
	private InstituicaoDeEnsino instituicao;
	
	public Horario() {
	}

    public Horario(String nome, Turno turno, LocalTime horaInicio, LocalTime horaFim, InstituicaoDeEnsino instituicao) {
        super();
        this.nome = nome;
        this.turno = turno;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.instituicao = instituicao;
    }

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	public LocalTime getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(LocalTime horaInicio) {
		this.horaInicio = horaInicio;
	}

	public LocalTime getHoraFim() {
		return horaFim;
	}

	public void setHoraFim(LocalTime horaFim) {
		this.horaFim = horaFim;
	}

	public InstituicaoDeEnsino getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(InstituicaoDeEnsino instituicao) {
		this.instituicao = instituicao;
	}

}
