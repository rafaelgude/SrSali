package br.com.srsali.srsali.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
public class AmbienteFerramenta implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
    @JoinColumn(name = "ferramenta_id")
	@NotNull(message = "Ferramenta é obrigatório.")
	private Ferramenta ferramenta;

	@ManyToOne
    @JoinColumn(name = "ambiente_id")
	@NotNull(message = "Ambiente é obrigatório.")
	private Ambiente ambiente;

	@Positive(message = "Quantidade é obrigatório.")
	private int quantidade;
	
    public AmbienteFerramenta() {
    }

    public AmbienteFerramenta(@NotNull Ferramenta ferramenta, @NotNull Ambiente ambiente, int quantidade) {
        super();
        this.ferramenta = ferramenta;
        this.ambiente = ambiente;
        this.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Ferramenta getFerramenta() {
        return ferramenta;
    }

    public void setFerramenta(Ferramenta ferramenta) {
        this.ferramenta = ferramenta;
    }

    public Ambiente getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

}
