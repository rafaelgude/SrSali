package br.com.srsali.srsali.models;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class AmbienteFerramenta implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	@EmbeddedId
	private AmbienteFerramentaId id = new AmbienteFerramentaId();

	@Positive(message = "Quantidade é obrigatório.")
	private int quantidade;
	
    public AmbienteFerramenta() {
    }

    public AmbienteFerramenta(@NotNull Ferramenta ferramenta, @NotNull Ambiente ambiente, int quantidade) {
        super();
        this.id.setFerramenta(ferramenta);
        this.id.setAmbiente(ambiente);
        this.quantidade = quantidade;
    }

    public Ferramenta getFerramenta() {
        return id.getFerramenta();
    }

    public void setFerramenta(Ferramenta ferramenta) {
        this.id.setFerramenta(ferramenta);
    }

    public Ambiente getAmbiente() {
        return id.getAmbiente();
    }

    public void setAmbiente(Ambiente ambiente) {
        this.id.setAmbiente(ambiente);
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

}
