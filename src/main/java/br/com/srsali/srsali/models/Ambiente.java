package br.com.srsali.srsali.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.srsali.srsali.enums.TipoAmbiente;

@Entity
public class Ambiente implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String nome;

	private int capacidadeAlunos;

	@JsonIgnore
	@ManyToOne
    @JoinColumn(name = "instituicao_id")
	private InstituicaoDeEnsino instituicao;

	@Enumerated
	private TipoAmbiente tipoAmbiente = TipoAmbiente.SALA_AULA;

	private boolean ativo;

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

}
