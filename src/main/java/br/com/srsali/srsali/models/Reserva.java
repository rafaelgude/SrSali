package br.com.srsali.srsali.models;

import java.io.Serializable;
import java.time.LocalDateTime;

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
	
	private	String nome;
	
	private	String email;
	
	private	String senha;
	
	private	String telefone;
	
	private	int	funcao;
	
	private	String convite;
	
	private	LocalDateTime dataHoraConvite;
	
	private	int	permissoes;
	
	private	InstituicaoDeEnsino	instituicao;

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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public int getFuncao() {
		return funcao;
	}

	public void setFuncao(int funcao) {
		this.funcao = funcao;
	}

	public String getConvite() {
		return convite;
	}

	public void setConvite(String convite) {
		this.convite = convite;
	}

	public LocalDateTime getDataHoraConvite() {
		return dataHoraConvite;
	}

	public void setDataHoraConvite(LocalDateTime dataHoraConvite) {
		this.dataHoraConvite = dataHoraConvite;
	}

	public int getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(int permissoes) {
		this.permissoes = permissoes;
	}

	public InstituicaoDeEnsino getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(InstituicaoDeEnsino instituicao) {
		this.instituicao = instituicao;
	}
	
}
