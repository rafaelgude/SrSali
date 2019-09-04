package br.com.srsali.srsali.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class InstituicaoDeEnsino implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String nome;
	
	private Usuario criador;
	
	private boolean ativo;
	
	public InstituicaoDeEnsino() {
	}
	
	public InstituicaoDeEnsino(String nome, Usuario criador, boolean ativo) {
		super();
		this.nome = nome;
		this.criador = criador;
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
	
	public Usuario getCriador() {
		return criador;
	}
	
	public void setCriador(Usuario criador) {
		this.criador = criador;
	}
	
	public boolean isAtivo() {
		return ativo;
	}
	
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
}