package br.com.srsali.srsali.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Sets;

import br.com.srsali.srsali.enums.Funcao;
import br.com.srsali.srsali.enums.Permissao;

@Entity
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String nome;
	
	@Column(unique = true)
	private String email;
	
	@JsonIgnore
	private String senha;
	
	private String telefone;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "usuario_funcao", joinColumns = @JoinColumn(name = "usuario_id"))
	@Column(name = "funcao_id")
	private Set<Funcao> funcoes = Sets.newHashSet(Funcao.USUARIO);
	
	private String convite;
	
	private LocalDateTime dataHoraConvite;
	
	@ElementCollection
    @CollectionTable(name = "usuario_permissao", joinColumns = @JoinColumn(name = "usuario_id"))
    @Column(name = "permissao_id")
    private Set<Permissao> permissoes = new HashSet<>();
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "instituicao_id")
	private InstituicaoDeEnsino instituicao;

	public Usuario() {
	}
	
    public Usuario(String nome, String email, String senha, String telefone, InstituicaoDeEnsino instituicao) {
        super();
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public String getSenha() {
        return senha;
    }

    @JsonProperty
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Set<Funcao> getFuncoes() {
        return funcoes;
    }

    public void setFuncoes(Set<Funcao> funcoes) {
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

    public Set<Permissao> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(Set<Permissao> permissoes) {
        this.permissoes = permissoes;
    }

    public InstituicaoDeEnsino getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(InstituicaoDeEnsino instituicao) {
        this.instituicao = instituicao;
    }

}
