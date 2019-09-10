package br.com.srsali.srsali.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Professor implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String nome;
	
	private String email;
	
	@OneToOne
	private Usuario usuario;
	
	@JsonIgnore
	@ManyToOne
    @JoinColumn(name = "instituicao_id")
	private InstituicaoDeEnsino instituicao;
	
	private boolean ativo;
	
	public Professor() {
    }
	
    public Professor(String email, String nome, Usuario usuario, InstituicaoDeEnsino instituicao, boolean ativo) {
        super();
        this.nome = nome;
        this.email = email;
        this.usuario = usuario;
        this.instituicao = instituicao;
        this.ativo = ativo;
    }

    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
	
}
