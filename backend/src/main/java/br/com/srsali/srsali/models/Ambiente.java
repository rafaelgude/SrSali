package br.com.srsali.srsali.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.srsali.srsali.enums.TipoAmbiente;

@Entity
public class Ambiente implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull(message = "Nome é obrigatório.")
	private String nome;

	@Positive(message = "Capacidade de Alunos é obrigatório.")
	private int capacidadeAlunos;

	@JsonIgnore
	@ManyToOne
    @JoinColumn(name = "instituicao_id")
	@NotNull(message = "Instituição é obrigatório.")
	private InstituicaoDeEnsino instituicao;

	@Enumerated
	@NotNull(message = "Tipo de Ambiente é obrigatório.")
	private TipoAmbiente tipoAmbiente = TipoAmbiente.SALA_AULA;

	private boolean ativo;
	
	@JsonIgnoreProperties(value = "ambiente")
	@OneToMany(mappedBy = "id.ambiente", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<AmbienteFerramenta> ferramentas = new HashSet<>();
	
    public Ambiente() {
    }
    
    public Ambiente(String nome, int capacidadeAlunos, InstituicaoDeEnsino instituicao, TipoAmbiente tipoAmbiente, boolean ativo) {
        super();
        this.nome = nome;
        this.capacidadeAlunos = capacidadeAlunos;
        this.instituicao = instituicao;
        this.tipoAmbiente = tipoAmbiente;
        this.ativo = ativo;
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

    public int getCapacidadeAlunos() {
        return capacidadeAlunos;
    }

    public void setCapacidadeAlunos(int capacidadeAlunos) {
        this.capacidadeAlunos = capacidadeAlunos;
    }

    public InstituicaoDeEnsino getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(InstituicaoDeEnsino instituicao) {
        this.instituicao = instituicao;
    }

    public TipoAmbiente getTipoAmbiente() {
        return tipoAmbiente;
    }

    public void setTipoAmbiente(TipoAmbiente tipoAmbiente) {
        this.tipoAmbiente = tipoAmbiente;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Set<AmbienteFerramenta> getFerramentas() {
        return ferramentas;
    }

    public void setFerramentas(Set<AmbienteFerramenta> ferramentas) {
        this.ferramentas.clear();
        this.ferramentas.addAll(ferramentas);
        this.ferramentas.forEach(x -> x.setAmbiente(this));
    }

}
