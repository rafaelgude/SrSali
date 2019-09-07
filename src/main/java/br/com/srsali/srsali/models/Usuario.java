package br.com.srsali.srsali.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.srsali.srsali.enums.Funcoes;

@Entity
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private	String nome;
	
	private	String email;
	
	private	String senha;
	
	private	String telefone;
	
	@ElementCollection(targetClass = Funcoes.class)
	@CollectionTable(name = "usuario_funcao", joinColumns = @JoinColumn(name = "usuario_id"))
	private	Set<Funcoes> funcoes = new HashSet<>();
	
	private	String convite;
	
	private	LocalDateTime dataHoraConvite;
	
//	@Enumerated
//	private	Set<Permissoes> permissoes;
	
	@ManyToOne
	@JoinColumn(name = "instituicao_id")
	private	InstituicaoDeEnsino	instituicao;

	public Usuario() {
		super();
		this.funcoes.add(Funcoes.USUARIO);
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

	public Set<Funcoes> getFuncoes() {
		return funcoes;
	}

	public void setFuncoes(Set<Funcoes> funcoes) {
		this.funcoes = funcoes;
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

//	public Set<Permissoes> getPermissoes() {
//		return permissoes;
//	}
//
//	public void setPermissoes(Set<Permissoes> permissoes) {
//		this.permissoes = permissoes;
//	}

	public InstituicaoDeEnsino getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(InstituicaoDeEnsino instituicao) {
		this.instituicao = instituicao;
	}
	
}
