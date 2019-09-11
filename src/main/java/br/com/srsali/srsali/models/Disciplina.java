package br.com.srsali.srsali.models;

import java.io.Serializable;
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

@Entity
public class Disciplina implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String nome;

	@JsonIgnore
	@ManyToOne
    @JoinColumn(name = "instituicao_id")
	private InstituicaoDeEnsino instituicao;

	private boolean ativo;
	
	@ManyToMany
    @JoinTable(name = "disciplina_ferramenta", 
               joinColumns = @JoinColumn(name = "disciplina_id"), 
               inverseJoinColumns = @JoinColumn(name = "ferramenta_id"))
    private Set<Ferramenta> ferramentas;

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
	
}
