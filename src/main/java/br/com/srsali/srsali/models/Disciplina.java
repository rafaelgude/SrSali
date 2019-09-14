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
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Disciplina implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull(message = "Nome é obrigatório.")
	private String nome;

	@JsonIgnore
	@ManyToOne
    @JoinColumn(name = "instituicao_id")
	@NotNull(message = "Instituição é obrigatório.")
	private InstituicaoDeEnsino instituicao;

	private boolean ativo;
	
	@ManyToMany
    @JoinTable(name = "disciplina_ferramenta", 
               joinColumns = @JoinColumn(name = "disciplina_id"), 
               inverseJoinColumns = @JoinColumn(name = "ferramenta_id"))
    private Set<Ferramenta> ferramentas = new HashSet<>();
	
	public Disciplina() {
	}
	
	public Disciplina(int id) {
	    this.id = id;
    }
	
	public Disciplina(String nome, InstituicaoDeEnsino instituicao, boolean ativo, Set<Ferramenta> ferramentas) {
        super();
        this.nome = nome;
        this.instituicao = instituicao;
        this.ativo = ativo;
        this.ferramentas = ferramentas;
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

    public Set<Ferramenta> getFerramentas() {
        return ferramentas;
    }

    public void setFerramentas(Set<Ferramenta> ferramentas) {
        this.ferramentas = ferramentas;
    }
    
    @JsonProperty
    public void setFerramentas(int[] ferramentas) {
        for (var x : ferramentas)
            this.ferramentas.add(new Ferramenta(x));
    }
	
}
