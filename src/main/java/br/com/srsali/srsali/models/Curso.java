package br.com.srsali.srsali.models;

import java.io.Serializable;
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Curso implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String nome;
	
	@JsonIgnore
	@ManyToOne
    @JoinColumn(name = "instituicao_id")
	private InstituicaoDeEnsino instituicao;
	
	private boolean ativo = true;
	
	@JsonIgnoreProperties(value = "ferramentas")
	@ManyToMany
	@JoinTable(name = "curso_disciplina", 
			   joinColumns = @JoinColumn(name = "curso_id"), 
			   inverseJoinColumns = @JoinColumn(name = "disciplina_id"))
	private Set<Disciplina> disciplinas = new HashSet<>();

	public Curso() {
	}

	public Curso(String nome, InstituicaoDeEnsino instituicao, boolean ativo, Set<Disciplina> disciplinas) {
        super();
        this.nome = nome;
        this.instituicao = instituicao;
        this.ativo = ativo;
        this.disciplinas = disciplinas;
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

	public InstituicaoDeEnsino getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(InstituicaoDeEnsino instituicao) {
		this.instituicao = instituicao;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

    public Set<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(Set<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }
	
}
